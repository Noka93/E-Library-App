package com.remidiousE.service;

import com.remidiousE.Exceptions.*;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.repositories.AuthorRepository;
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
class AuthorServiceImplTest {

    @Autowired

    private EntityManager entityManager;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorRegistrationRequest authorRegistrationRequest;

    @BeforeEach
    void setUp() {
        authorRegistrationRequest = buildAuthorRegistration();
    }

    @Test
    void testToRegisterNewAuthor() throws AuthorRegistrationException {
        AuthorRegistrationResponse foundAuthor = authorService.registerNewAuthor(authorRegistrationRequest);
        System.out.println(foundAuthor);
        assertNotNull(foundAuthor);
    }
    @Test
    void testFindAuthorById() throws AuthorNotFoundException {
        Author author = new Author();
        authorRepository.save(author);

        Long authorId = author.getId();

        Optional<Author> foundAuthor = authorService.findAuthorById(authorId);

        assertTrue(foundAuthor.isPresent());
        assertEquals(authorId, foundAuthor.get().getId());
    }

    @Test
    void testToFindAllAuthor() throws AuthorRegistrationException {
        AuthorRegistrationRequest author1 = new AuthorRegistrationRequest();
        AuthorRegistrationRequest author2 = new AuthorRegistrationRequest();
        authorService.registerNewAuthor(author1);
        authorService.registerNewAuthor(author2);

        List<Author> authors = authorService.findAllAuthor();

        assertEquals(2, authors.size());
    }
    private static AuthorRegistrationRequest buildAuthorRegistration(){
        AuthorRegistrationRequest authorRegistrationRequest = new AuthorRegistrationRequest();
        authorRegistrationRequest.setFirstName("Chibuzo");
        authorRegistrationRequest.setLastName("Alexander");
        authorRegistrationRequest.setUsername("Chibu199");
        authorRegistrationRequest.setPassword("abcd");
        authorRegistrationRequest.setEmail("chibuzoAlex@gmail.com");
        authorRegistrationRequest.setPhoneNumber("908345");
        authorRegistrationRequest.setHouseNumber("No. 44");
        authorRegistrationRequest.setStreet("Hebert Marcurly");
        authorRegistrationRequest.setLga("Yaba");
        authorRegistrationRequest.setState("Lagos");
        return authorRegistrationRequest;
    }

    @Test
    void testUpdateAuthorProfileById() throws AuthorNotFoundException {
        Author author = new Author();
        author.setFirstName("Femi");
        author.setLastName("Michael");
        author.setEmail("femi@gmail.com");
        author.setUsername("Femo02");
        author.setPhoneNumber("08134757992");

        Author savedAuthor = authorRepository.save(author);

        Author updatedAuthor = new Author();
        updatedAuthor.setFirstName("Femz");
        updatedAuthor.setEmail("femi@example.com");

        Author returnedAuthor = authorService.updateAuthorProfileById(savedAuthor.getId(), updatedAuthor);

        assertNotNull(returnedAuthor);
        assertEquals(savedAuthor.getId(), returnedAuthor.getId());
        assertEquals(savedAuthor.getFirstName(), returnedAuthor.getFirstName());
        assertEquals(savedAuthor.getLastName(), returnedAuthor.getLastName());
        assertEquals(savedAuthor.getEmail(), returnedAuthor.getEmail());
        assertEquals(savedAuthor.getUsername(), returnedAuthor.getUsername());
        assertEquals(savedAuthor.getPhoneNumber(), returnedAuthor.getPhoneNumber());
    }


    @Test
    void testToDeleteAuthorById() throws AuthorRegistrationException, AuthorNotFoundException {
        authorService.registerNewAuthor(authorRegistrationRequest);

        List<Author> authors = authorService.findAllAuthor();

        Author lastRegisteredAuthor = authors.get(authors.size() - 1);

        authorService.deleteAuthorById(lastRegisteredAuthor.getId());

        authors = authorService.findAllAuthor();

        assertEquals(0, authors.size());
    }

    @Test
    void testAuthorCanCheckoutBook() throws BookNotFoundException, BookNotAvailableException {
        Book book = new Book();
        book.setTitle("Sample Book");
        book.setAvailable(true);
        entityManager.persist(book);
        entityManager.flush();

        Long bookId = book.getId();

        BookCheckoutResponse response = authorService.checkoutBook(bookId);

        assertNotNull(response);
        assertEquals(bookId, response.getBookId());
        assertEquals("Sample Book", response.getTitle());
        assertNotNull(response.getCheckedOutBy());
    }


}