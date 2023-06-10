package com.remidiousE.service;

import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.BookCheckoutResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.dto.response.BookReservationResponse;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    BookRegistrationResponse registerNewBook(BookRegistrationRequest bookRequest) throws BookRegistrationException;

    Optional<Book> findBookById(Long id) throws BookNotFoundException;

    List<Book> findAllBooks();

    Book updateBookById(Long bookId, Book book) throws BookNotFoundException;

    void deleteBookById(Long bookId);

    List<Book> searchBookByTitle(String title) throws BookNotFoundException;
    List<Book> findBooksByAuthorName(String firstname, String lastname);

    BookReservationResponse reserveBook(Long adminId, Long bookId) throws BookNotFoundException, BookNotAvailableException;

    BookCheckoutResponse checkoutBook(Long bookId) throws BookNotFoundException, BookNotAvailableException;

    void returnBookAfterFiveDays(User user, Book book);

    AdminLoginResponse loginAdmin(AdminRegistrationRequest registrationRequest);
}
