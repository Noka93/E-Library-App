package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminNotFoundException;
import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Mapper.AdminMapper;
import com.remidiousE.Mapper.BookMapper;
import com.remidiousE.dto.request.*;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.model.Status;
import com.remidiousE.repositories.AdminRepository;
import com.remidiousE.repositories.AuthorRepository;
import com.remidiousE.repositories.BookRepository;
import com.remidiousE.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final UserRepository userRepository;

    private List<Book> borrowedBooks = new ArrayList<>();
    @Override
    public AdminRegistrationResponse registerNewAdmin(AdminRegistrationRequest adminRequest){
    Admin newAdmin = AdminMapper.map(adminRequest);
    Admin savedAdmin = adminRepository.save(newAdmin);

    return AdminMapper.map(savedAdmin);

    }
    @Override
    public Optional<Admin>  findAdminById(Long id) throws AdminNotFoundException {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isPresent()) {
            return foundAdmin;
        } else {
            throw new AdminNotFoundException("Admin not found with ID: " + id);
        }
    }
    @Override
    public Optional<Admin> findAdminByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Invalid username");
        }else {
            return adminRepository.findAdminByUsername(username);
        }
    }
    @Override
    public List<Admin> findAllAdmin() {
        return adminRepository.findAll();
    }

    @Override
    public Admin updateAdminById(Long adminId, Admin admin) throws AdminNotFoundException {
        Optional<Admin> existingAdmin = adminRepository.findById(adminId);
        if (existingAdmin.isEmpty()) {
            throw new AdminNotFoundException("Admin not found.");
        }
        Admin foundAdmin = existingAdmin.get();

        if (admin.getFirstName() != null && !admin.getFirstName().isEmpty()) {
            foundAdmin.setFirstName(admin.getFirstName());
        }
        if (admin.getLastName() != null && !admin.getLastName().isEmpty()) {
            foundAdmin.setLastName(admin.getLastName());
        }
        if (admin.getEmail() != null && !admin.getEmail().isEmpty()) {
            foundAdmin.setEmail(admin.getEmail());
        }
        if (admin.getUsername() != null && !admin.getUsername().isEmpty()) {
            foundAdmin.setUsername(admin.getUsername());
        }
        if (admin.getPhoneNumber() != null && !admin.getPhoneNumber().isEmpty()) {
            foundAdmin.setPhoneNumber(admin.getPhoneNumber());
        }

        return adminRepository.save(foundAdmin);
    }

    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }
    @Override
    public AdminLoginResponse loginAdmin(AdminLoginRequest request) {
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        String username = request.getUserName();
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
    public List<Book> findBooksByAuthorName(String firstname, String lastname) {
        return bookRepository.findBookByAuthor_FirstNameAndAuthor_LastName(firstname, lastname);
    }

    @Override
    public BookReservationResponse reserveBook(Long adminId, Long bookId) throws BookNotFoundException, BookNotAvailableException {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book not found.");
        }

        Book book = optionalBook.get();
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
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(() -> {
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            optionalBook.ifPresent(book -> {
                if (book.getStatus() == Status.RESERVED) {
                    book.setStatus(Status.AVAILABLE);
                    book.setReservedBy(null);
                    book.setReservationTime(null);
                    bookRepository.save(book);
                }
            });
        }, 2, TimeUnit.DAYS);

        executorService.shutdown();
    }
    @Override
    public void deleteAuthorById(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
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
        return "Welcome User";
    }
    @Override
    public void returnBookAfterFiveDays(Admin admin, Book book) {
        if (hasBorrowedBook(admin, book)) {
            if (getBorrowedDays(admin, book) >= 5) {
                int daysLate = getBorrowedDays(admin, book) - 5;
                double fine = daysLate * 10;

                book.setAvailable(true);
                book.setBorrowedDays(0);
                removeBorrowedBook(admin, book);

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
    private boolean hasBorrowedBook(Admin admin, Book book) {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(admin)) {
                return true;
            }
        }
        return false;
    }
    private int getBorrowedDays(Admin admin, Book book) {
        LocalDate borrowDate = book.getBorrowedDate();
        LocalDate currentDate = LocalDate.now();
        int borrowedDays = 0;

        while (borrowDate.isBefore(currentDate)) {
            borrowDate = borrowDate.plusDays(1);
            borrowedDays++;
        }
        return borrowedDays;
    }
    private void removeBorrowedBook(Admin admin, Book book) {
        for (Iterator<Book> iterator = borrowedBooks.iterator(); iterator.hasNext();) {
            Book borrowedBook = iterator.next();
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(admin)) {
                iterator.remove();
                break;
            }
        }
    }

}



