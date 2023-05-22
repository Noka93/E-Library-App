package com.remidiousE.service;

import com.remidiousE.Exceptions.AuthorRegistrationException;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorServiceImplTest {
    @Autowired
    private AuthorService authorService;

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
    void testToFindAuthorById() throws AuthorRegistrationException {
        AuthorRegistrationResponse authorRegistrationResponse = authorService.registerNewAuthor(authorRegistrationRequest);

        Long registeredAuthorId = 1L;
        authorRegistrationResponse.setId(registeredAuthorId);

        assertTrue(registeredAuthorId != null && registeredAuthorId > 0);

        Optional<Author> foundAuthor = authorService.findAuthorById(registeredAuthorId);

        assertTrue(foundAuthor.isPresent());

        assertEquals(registeredAuthorId, foundAuthor.get().getId());
    }
    @Test
    void testToFindAllAuthor() throws AuthorRegistrationException {
        AuthorRegistrationRequest author1 = new AuthorRegistrationRequest();
        AuthorRegistrationRequest author2 = new AuthorRegistrationRequest();
        authorService.registerNewAuthor(author1);
        authorService.registerNewAuthor(author2);

        List<Author> authors = authorService.findAllAuthor();

        assertEquals(4, authors.size());
    }
    private static AuthorRegistrationRequest buildAuthorRegistration(){
        AuthorRegistrationRequest authorRegistrationRequest = new AuthorRegistrationRequest();
        authorRegistrationRequest.setFirstName("Chibuzo");
        authorRegistrationRequest.setLastName("Alexander");
        authorRegistrationRequest.setPassword("abcd");
        authorRegistrationRequest.setEmail("chibuzoAlex@gmail.com");
        authorRegistrationRequest.setHouseNumber("No. 44");
        authorRegistrationRequest.setStreet("Hebert Marcurly");
        authorRegistrationRequest.setTown("Akoka");
        authorRegistrationRequest.setLga("Yaba");
        authorRegistrationRequest.setState("Lagos");
        return authorRegistrationRequest;
    }

    @Test
    void testToDeleteAuthorById() throws AuthorRegistrationException {
        authorService.registerNewAuthor(authorRegistrationRequest);

        List<Author> authors = authorService.findAllAuthor();

        Author lastRegisteredAuthor = authors.get(authors.size() - 1);

        authorService.deleteAuthorById(lastRegisteredAuthor.getId());

        authors = authorService.findAllAuthor();

        assertEquals(1, authors.size());
    }

}