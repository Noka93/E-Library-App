package com.remidiousE.service;

import com.remidiousE.Exceptions.*;
import com.remidiousE.Mapper.UserMapper;
import com.remidiousE.dto.request.UserLoginRequest;
import com.remidiousE.dto.request.UserRegistrationRequest;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.dto.response.BookReservationResponse;
import com.remidiousE.dto.response.UserLoginResponse;
import com.remidiousE.dto.response.UserRegistrationResponse;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;
import com.remidiousE.model.Status;
import com.remidiousE.repositories.BookRepository;
import com.remidiousE.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private List<Book> borrowedBooks = new ArrayList<>();

    @Override
    public UserRegistrationResponse registerNewUser(UserRegistrationRequest userRequest)throws UserRegistrationException {
        User newUser = UserMapper.map(userRequest);
        User savedUser = userRepository.save(newUser);

        return UserMapper.map(savedUser);
    }
    @Override
    public Optional<User> findUserById(Long id) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            return foundUser;
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserProfileById(Long userId, User user) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }
        User foundUser = existingUser.get();

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            foundUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            foundUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            foundUser.setEmail(user.getEmail());
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            foundUser.setUsername(user.getUsername());
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            foundUser.setPhoneNumber(user.getPhoneNumber());
        }

        return userRepository.save(foundUser);
    }
    @Override
    public List<Book> searchBookByTitle(String title) throws BookNotFoundException {
        List<Book> books = bookRepository.searchBookByTitle(title);
        if (!books.isEmpty()) {
            return books;
        } else {
            throw new BookNotFoundException("No books found with the given title");
        }
    }
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Invalid username");
        }else {
            return userRepository.findUserByUsername(username);
        }
    }
    @Override
    public UserLoginResponse loginUser(UserLoginRequest request) {
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        String username = request.getUserName();
        String password = request.getPassword();
        if (authenticate(username, password)) {
            userLoginResponse.setMessage("You have logged in successfully");
        } else {
            userLoginResponse.setMessage("Failed to log in");
        }
        return userLoginResponse;
    }
    private boolean authenticate(String username, String password) {
        if (username.equals("SexyFavour") && password.equals("2233")) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<Book> findBookByAuthor_FirstNameAndAuthor_LastName(String firstname, String lastname) {
        return bookRepository.findBookByAuthor_FirstNameAndAuthor_LastName(firstname, lastname);
    }
    @Override
    public BookReservationResponse reserveBook(Long userId, Long bookId) throws BookNotFoundException, BookNotAvailableException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found."));

        if (book.getStatus() != Status.AVAILABLE) {
            throw new BookNotAvailableException("The book is not available for reservation.");
        }

        book.setStatus(Status.RESERVED);
        book.setReservedBy(userId);
        book.setReservationTime(LocalDate.now());
        bookRepository.save(book);

        scheduleStatusChange(bookId);

        BookReservationResponse response = new BookReservationResponse();
        response.setMessage("Book reserved successfully.");
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        return response;
    }

    private void scheduleStatusChange(Long bookId) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Book book = bookRepository.findById(bookId).orElse(null);
                if (book != null && book.getStatus() == Status.RESERVED) {
                    book.setStatus(Status.AVAILABLE);
                    book.setReservedBy(null);
                    book.setReservationTime(null);
                    bookRepository.save(book);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2L * 24L * 60L * 60L * 1000L);
    }

    @Override
    public BookCheckoutResponse checkoutBook(Long bookId) throws BookNotFoundException, BookNotAvailableException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + bookId));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for checkout");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        BookCheckoutResponse response = new BookCheckoutResponse();
        response.setBookId(book.getId());
        response.setTitle(book.getTitle());
        response.setCheckedOutBy((String) getLoggedInUser());

        return response;
    }
    private Object getLoggedInUser() {
        return "User";
    }
    @Override
    public void returnBookAfterFiveDays(User user, Book book) {
        if (hasBorrowedBook(user, book)) {
            if (getBorrowedDays(user, book) >= 5) {
                int daysLate = getBorrowedDays(user, book) - 5;
                double fine = daysLate * 10;

                book.setAvailable(true);
                book.setBorrowedDays(0);
                removeBorrowedBook(user, book);

                System.out.println("Book returned successfully.");
                if (fine > 0) {
                    System.out.println("You have been fined $" + fine + " for returning the book " + daysLate + " day(s) late.");
                }
            } else {
                System.out.println("You can only return the book after 5 days of borrowing.");
            }
        } else {
            System.out.println("You have not borrowed this book.");
        }
    }
    private boolean hasBorrowedBook(User user, Book book) {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(user)) {
                return true;
            }
        }
        return false;
    }
    private int getBorrowedDays(User user, Book book) {
        LocalDate borrowDate = book.getBorrowedDate();
        LocalDate currentDate = LocalDate.now();
        int borrowedDays = 0;

        while (borrowDate.isBefore(currentDate)) {
            borrowDate = borrowDate.plusDays(1);
            borrowedDays++;
        }
        return borrowedDays;
    }
    private void removeBorrowedBook(User user, Book book) {
        for (Iterator<Book> iterator = borrowedBooks.iterator(); iterator.hasNext();) {
            Book borrowedBook = iterator.next();
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(user)) {
                iterator.remove();
                break;
            }
        }
    }

}
