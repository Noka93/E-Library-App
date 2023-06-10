package com.remidiousE.service;

import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.UserNotFoundException;
import com.remidiousE.Exceptions.UserRegistrationException;
import com.remidiousE.dto.request.UserLoginRequest;
import com.remidiousE.dto.request.UserRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    UserRegistrationResponse registerNewUser(UserRegistrationRequest memberRequest) throws UserRegistrationException;

    Optional<User> findUserById(Long id) throws UserNotFoundException;

    List<User> findAllUsers();

    List<Book> findBookByAuthor_FirstNameAndAuthor_LastName(String firstname, String lastname);

    User updateUserProfileById(Long userId, User user) throws UserNotFoundException;

    void  deleteUserById(Long id) throws UserNotFoundException;

    Optional<User> findUserByUsername(String username);

    UserLoginResponse loginUser(UserLoginRequest request);


    List<Book> searchBookByTitle(String title) throws BookNotFoundException;

    BookReservationResponse reserveBook(Long userId, Long bookId) throws BookNotFoundException, BookNotAvailableException;

    BookCheckoutResponse checkoutBook(Long bookId) throws BookNotFoundException, BookNotAvailableException;

    void returnBookAfterFiveDays(User user, Book book);
}
