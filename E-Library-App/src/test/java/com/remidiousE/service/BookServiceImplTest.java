package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminNotFoundException;
import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.model.Status;
import com.remidiousE.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class BookServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private BookRegistrationRequest bookRegistrationRequest;

    @BeforeEach
    void setUp() {
        bookRegistrationRequest = buildBookRegistration();
    }

    @Test
    void testToRegisterNewBook() throws BookRegistrationException {
        BookRegistrationResponse foundBook = bookService.registerNewBook(bookRegistrationRequest);
        assertNotNull(foundBook);
    }

    @Test
    void testFindBookById() throws BookNotFoundException {
        Book book = new Book();
        book.setTitle("Sample Book");
        bookRepository.save(book);

        Long bookId = book.getId();
        Optional<Book> foundBook = bookService.findBookById(bookId);

        assertTrue(foundBook.isPresent());
        assertEquals(bookId, foundBook.get().getId());
    }
    @Test
    void testFindAllBooks() throws BookRegistrationException, BookNotFoundException {
        bookService.registerNewBook(bookRegistrationRequest);
        List<Book> books = bookService.findAllBooks();
        Book lastRegisteredBook = books.get(books.size() - 1);
        Optional<Book> foundBook = bookService.findBookById(lastRegisteredBook.getId());
        assertNotNull(foundBook);
    }

    private static BookRegistrationRequest buildBookRegistration() {
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest();
        bookRegistrationRequest.setTitle("The God's Must Be Crazy");
        bookRegistrationRequest.setIsbn("1234567");
        bookRegistrationRequest.setDescription("This is one of the best books for making you look beyond the future");
        bookRegistrationRequest.setYear(1990);

        Author author = new Author();
        author.setFirstName("Michael");
        author.setLastName("Bush");
        bookRegistrationRequest.setAuthorName(String.valueOf(author));

        return bookRegistrationRequest;
    }
    @Test
    void testUpdateAdminProfileById() throws AdminNotFoundException, BookNotFoundException {
        Book book = new Book();
        book.setTitle("Good's made man");
        book.setDescription("Now we know");
        book.setIsbn("23456");

        Book savedBook = bookRepository.save(book);

        Book updatedBook = new Book();
        updatedBook.setTitle("God made man");
        updatedBook.setIsbn("23456788");

        Book returnedBook = bookService.updateBookById(savedBook.getId(), updatedBook);

        assertNotNull(returnedBook);
        assertEquals(savedBook.getId(), returnedBook.getId());
        assertEquals(updatedBook.getTitle(), returnedBook.getTitle());
        assertEquals(savedBook.getDescription(), returnedBook.getDescription());
        assertEquals(updatedBook.getIsbn(), returnedBook.getIsbn());
    }

    @Test
    void testSearchBookByTitle() throws BookNotFoundException {
        Book book1 = new Book();
        Book book2 = new Book();

        book1.setTitle("Let Love Lead");
        book2.setTitle("Love is greater");

        bookRepository.save(book1);
        bookRepository.save(book2);

        String titleToSearch = "Let Love Lead";
        List<Book> foundBooks = bookService.searchBookByTitle(titleToSearch);

        assertEquals(1, foundBooks.size());
        assertEquals(book1.getTitle(), foundBooks.get(0).getTitle());
    }


    @Test
    void testToDeleteBookById() throws BookRegistrationException {
        bookService.registerNewBook(bookRegistrationRequest);

        List<Book> books = bookService.findAllBooks();

        Book lastRegisteredBook = books.get(books.size() - 1);

        bookService.deleteBookById(lastRegisteredBook.getId());

        books = bookService.findAllBooks();

        assertEquals(0, books.size());
    }
    @Test
    void testCheckoutBook() throws BookNotFoundException, BookNotAvailableException {
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAvailable(true);
        entityManager.persist(book);
        entityManager.flush();

        Long bookId = book.getId();

        BookCheckoutResponse response = bookService.checkoutBook(bookId);

        assertNotNull(response);
        assertEquals(bookId, response.getBookId());
        assertEquals("Sample Book", response.getTitle());
        assertNotNull(response.getCheckedOutBy());
    }
}
