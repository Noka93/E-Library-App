package com.remidiousE.service;

import com.remidiousE.Exceptions.AuthorNotFoundException;
import com.remidiousE.Exceptions.AuthorRegistrationException;
import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.dto.request.AuthorLoginRequest;
import com.remidiousE.dto.request.AuthorProfileUpdateRequest;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorLoginResponse;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {

    AuthorRegistrationResponse registerNewAuthor(AuthorRegistrationRequest authorRequest) throws AuthorRegistrationException;

    Optional<Author> findAuthorById(Long id) throws AuthorNotFoundException;

    Optional<Author> findAuthorByUsername(String username);

    List<Author> findAllAuthor();

    Author updateAuthorProfileById(Long authorId, Author author) throws AuthorNotFoundException;

    void deleteAuthorById(Long id) throws AuthorNotFoundException;

    List<Book> searchBookByTitle(String title);

    List<Book> findBooksByAuthorName(String firstname, String lastname);

    AuthorLoginResponse loginAuthor(AuthorLoginRequest request);

    BookCheckoutResponse checkoutBook(Long bookId) throws BookNotFoundException, BookNotAvailableException;

    void returnBookAfterFiveDays(Author author, Book book);
}
