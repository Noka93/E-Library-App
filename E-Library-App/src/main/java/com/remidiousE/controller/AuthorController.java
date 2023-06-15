package com.remidiousE.controller;

import com.remidiousE.Exceptions.AdminNotFoundException;
import com.remidiousE.Exceptions.AuthorNotFoundException;
import com.remidiousE.Exceptions.AuthorRegistrationException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.service.AuthorService;
import com.remidiousE.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @PostMapping("/create-author")
    public ResponseEntity<AuthorRegistrationResponse> registerNewAuthor(@RequestBody AuthorRegistrationRequest authorRegistrationRequest) throws AuthorRegistrationException {
        AuthorRegistrationResponse response = authorService.registerNewAuthor(authorRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/find-author/{authorId}")
    public Optional<Author> findAuthorById(@PathVariable("authorId") Long authorId) throws AuthorNotFoundException {
        return authorService.findAuthorById(authorId);
    }
    @GetMapping("/get-author/{username}")
    public Optional<Author> findAuthorByUsername(@PathVariable("username") String username) throws AuthorNotFoundException {
        return authorService.findAuthorByUsername(username);
    }
    @GetMapping("/get-authors")
    public List<Author> findAllAuthor() throws AuthorNotFoundException {
        return authorService.findAllAuthor();
    }
    @GetMapping("/author-find-book/{author_name}")
    public List<Book> findBooksByAuthorName(@PathVariable("author_name") String firstname, String lastname) throws AdminNotFoundException {
        return authorService.findBooksByAuthorName(firstname, lastname);
    }
    @GetMapping("/author-book-title/{title}")
    public List<Book> searchBookByTitle(@PathVariable("title") String title) throws BookNotFoundException {
        return bookService.searchBookByTitle(title);
    }
    @PutMapping("/update-author/{id}")
    public Author updateAuthorById(@PathVariable("id") Long authorId, @RequestBody Author author) throws AuthorNotFoundException {
        return authorService.updateAuthorProfileById(authorId, author);
    }
    @DeleteMapping("/delete-author/{id}")
    public String deleteAuthorById(@PathVariable("id") Long authorId) throws AuthorNotFoundException {
        authorService.deleteAuthorById(authorId);
        return "Author has been deleted successfully";
    }

}
