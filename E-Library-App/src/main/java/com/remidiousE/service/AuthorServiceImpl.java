package com.remidiousE.service;

import com.remidiousE.Exceptions.*;
import com.remidiousE.Mapper.AuthorMapper;
import com.remidiousE.dto.request.AuthorLoginRequest;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorLoginResponse;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.repositories.AuthorRepository;
import com.remidiousE.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor

public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private List<Book> borrowedBooks = new ArrayList<>();

    @Override
    public AuthorRegistrationResponse registerNewAuthor(AuthorRegistrationRequest authorRequest) throws AuthorRegistrationException {

        Author newAuthor = AuthorMapper.map(authorRequest);

        Author savedAuthor = authorRepository.save(newAuthor);

        return AuthorMapper.map(savedAuthor);

    }
    @Override
    public Optional<Author> findAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> foundAuthor = authorRepository.findById(id);
        if (foundAuthor.isPresent()) {
            return foundAuthor;
        } else {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }
    }

    @Override
    public Optional<Author> findAuthorByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Invalid username");
        }else {
            return authorRepository.findAuthorByUsername(username);
        }
    }
    @Override
    public List<Author> findAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author updateAuthorProfileById(Long authorId, Author author) throws AuthorNotFoundException {
        Optional<Author> existingAuthor = authorRepository.findById(authorId);
        if (existingAuthor.isEmpty()) {
            throw new AuthorNotFoundException("Author not found.");
        }
        Author foundAuthor = existingAuthor.get();

        if (author.getFirstName() != null && !author.getFirstName().isEmpty()) {
            foundAuthor.setFirstName(author.getFirstName());
        }
        if (author.getLastName() != null && !author.getLastName().isEmpty()) {
            foundAuthor.setLastName(author.getLastName());
        }
        if (author.getEmail() != null && !author.getEmail().isEmpty()) {
            foundAuthor.setEmail(author.getEmail());
        }
        if (author.getUsername() != null && !author.getUsername().isEmpty()) {
            foundAuthor.setUsername(author.getUsername());
        }
        if (author.getPhoneNumber() != null && !author.getPhoneNumber().isEmpty()) {
            foundAuthor.setPhoneNumber(author.getPhoneNumber());
        }

        return authorRepository.save(foundAuthor);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
    @Override
    public List<Book> searchBookByTitle(String title) {
        List<Book> searchResults = bookRepository.searchBookByTitle(title);
        return searchResults;
    }

    @Override
    public List<Book> findBooksByAuthorName(String firstname, String lastname) {
        return bookRepository.findBookByAuthor_FirstNameAndAuthor_LastName(firstname, lastname);
    }
    @Override
    public AuthorLoginResponse loginAuthor(AuthorLoginRequest request) {
        AuthorLoginResponse authorLoginResponse = new AuthorLoginResponse();

        String username = request.getUserName();
        String password = request.getPassword();

        if (authenticate(username, password)) {
            authorLoginResponse.setMessage("You have logged in successfully");
        } else {
            authorLoginResponse.setMessage("Failed to log in");
        }

        return authorLoginResponse;
    }
    private boolean authenticate(String username, String password) {
        return username.equals("admin") && password.equals("password");
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
    public void returnBookAfterFiveDays(Author author, Book book) {
        if (hasBorrowedBook(author, book)) {
            if (getBorrowedDays(author, book) >= 5) {
                int daysLate = getBorrowedDays(author, book) - 5;
                double fine = daysLate * 10;
                book.setAvailable(true);
                book.setBorrowedDays(0);
                removeBorrowedBook(author, book);
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
    private boolean hasBorrowedBook(Author author, Book book) {
        for (Book borrowedBook : borrowedBooks) {
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(author)) {
                return true;
            }
        }
        return false;
    }
    private int getBorrowedDays(Author author, Book book) {
        LocalDate borrowDate = book.getBorrowedDate();
        LocalDate currentDate = LocalDate.now();
        int borrowedDays = 0;

        while (borrowDate.isBefore(currentDate)) {
            borrowDate = borrowDate.plusDays(1);
            borrowedDays++;
        }
        return borrowedDays;
    }
    private void removeBorrowedBook(Author author, Book book) {
        for (Iterator<Book> iterator = borrowedBooks.iterator(); iterator.hasNext();) {
            Book borrowedBook = iterator.next();
            if (borrowedBook.equals(book) && borrowedBook.getBorrower().equals(author)) {
                iterator.remove();
                break;
            }
        }
    }


}
