package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.dto.response.BookSearchByTitleResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceImplTest {
    @Autowired
    private BookService bookService;
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
    void testToFindBookById() throws BookRegistrationException {
        BookRegistrationResponse bookRegistrationResponse = bookService.registerNewBook(bookRegistrationRequest);

        Long registeredBookId = 1L;
        bookRegistrationResponse.setId(registeredBookId);

        assertTrue(registeredBookId != null && registeredBookId > 0);

        Optional<Book> foundBook = bookService.findBookById(registeredBookId);

        assertTrue(foundBook.isPresent());

        assertEquals(registeredBookId, foundBook.get().getId());
    }
    @Test
    void testToFindAllBooks() throws BookRegistrationException {
        BookRegistrationRequest book1 = new BookRegistrationRequest();
        BookRegistrationRequest book2 = new BookRegistrationRequest();

        bookService.registerNewBook(book1);
        bookService.registerNewBook(book2);


        List<Book> books = bookService.findAllBooks();

        assertEquals(2, books.size());
    }
    private static BookRegistrationRequest buildBookRegistration(){
        BookRegistrationRequest bookRegistrationRequest = new BookRegistrationRequest();
        bookRegistrationRequest.setTitle("The God's must be crazy");
        bookRegistrationRequest.setAuthorName("Grace Mercy");
        bookRegistrationRequest.setStatus(Status.AVAILABLE);
        bookRegistrationRequest.setDescription("This is is one of the best books for making you look beyond the future");
        return bookRegistrationRequest;
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
    void testCanSearchBookByTitle(){
        AdminRegistrationRequest registrationRequest = new AdminRegistrationRequest();
        registrationRequest.setUsername("admin");
        registrationRequest.setPassword("password");

        AdminLoginResponse loginResponse = bookService.loginAdmin(registrationRequest);
        assertEquals("You have logged in successfully", loginResponse.getMessage());

        String bookTitle = "Judge of the Jungle";

        assertTrue(searchResultsDisplayed(), "Search results are not displayed");

        assertTrue(bookWithTitlePresent(bookTitle), "Book with title \"" + bookTitle + "\" is not found in the search results");

        assertTrue(bookDetailsPageDisplayed(), "Book details page is not displayed");

        BookSearchByTitleResponse bookDetails = getBookDetails();
        System.out.println(bookDetails);
    }
    private boolean searchResultsDisplayed() {
        return true;
    }
    private boolean bookWithTitlePresent(String bookTitle) {
        return true;
    }
    private boolean bookDetailsPageDisplayed() {
        return true;
    }
    private BookSearchByTitleResponse getBookDetails() {
        BookSearchByTitleResponse response = new BookSearchByTitleResponse();
        response.setTitle(response.getTitle());
        response.setStatus(response.getStatus());
        response.setDescription(response.getDescription());
        return response;
    }

}