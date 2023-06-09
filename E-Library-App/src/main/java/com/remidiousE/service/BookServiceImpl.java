package com.remidiousE.service;

import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.Mapper.BookMapper;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.dto.response.BookReservationResponse;
import com.remidiousE.model.*;
import com.remidiousE.repositories.AuthorRepository;
import com.remidiousE.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private List<Book> borrowedBooks = new ArrayList<>();

    @Override
    public BookRegistrationResponse registerNewBook(BookRegistrationRequest bookRequest) throws BookRegistrationException {
        try {
            Book newBook = BookMapper.map(bookRequest);
            if (newBook.getAuthor().getId() == null) {
                Author savedAuthor = authorRepository.save(newBook.getAuthor());
                newBook.setAuthor(savedAuthor);
            }
            Book savedBook = bookRepository.save(newBook);
            return BookMapper.map(savedBook);
        } catch (Exception e) {
            throw new BookRegistrationException("Failed to register a new book.", e);
        }
    }
    @Override
    public Optional<Book> findBookById(Long id) throws BookNotFoundException {
        Optional<Book> foundBook = bookRepository.findById(id);
        if (foundBook.isPresent()) {
            return foundBook;
        } else {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBookById(Long bookId, Book book) throws BookNotFoundException{
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isEmpty()) {
            throw new BookNotFoundException("Book not found.");
        }
        Book foundBook = existingBook.get();

        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            foundBook.setTitle(book.getTitle());
        }
        if (book.getDescription() != null && !book.getDescription().isEmpty()) {
            foundBook.setDescription(book.getDescription());
        }

        if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            foundBook.setIsbn(book.getIsbn());
        }

        return bookRepository.save(foundBook);

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
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> findBooksByAuthorName(String firstname, String lastname) {
        return bookRepository.findBookByAuthor_FirstNameAndAuthor_LastName(firstname, lastname);
    }

    @Override
    public BookReservationResponse reserveBook(Long adminId, Long bookId) throws BookNotFoundException, BookNotAvailableException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found."));

        if (book.getStatus() != Status.AVAILABLE) {
            throw new BookNotAvailableException("The book is not available for reservation.");
        }

        book.setStatus(Status.RESERVED);
        book.setReservedBy(adminId);
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
        response.setCheckedOutBy(getLoggedInUser().toString());

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

    @Override
    public AdminLoginResponse loginAdmin(AdminRegistrationRequest request) {
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        String username = request.getUsername();
        String password = request.getPassword();

        if (authenticate(username, password)) {
            adminLoginResponse.setMessage("You have logged in successfully");
        } else {
            adminLoginResponse.setMessage("Failed to log in");
        }

        return adminLoginResponse;
    }

    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("password");
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
        Iterator<Book> iterator = borrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book borrowedBook = iterator.next();
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(user)) {
                iterator.remove();
                break;
            }
        }
    }
}
