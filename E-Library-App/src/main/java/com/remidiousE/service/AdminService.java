package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminNotFoundException;
import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.BookNotAvailableException;
import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.dto.request.AdminLoginRequest;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.*;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    AdminRegistrationResponse registerNewAdmin(AdminRegistrationRequest adminRequest) throws AdminRegistrationException;

    Optional<Admin> findAdminById(Long id) throws AdminNotFoundException;

    Optional<Admin> findAdminByUsername(String username) throws AdminNotFoundException ;

    List<Admin> findAllAdmin();

    Admin updateAdminById(Long adminId, Admin admin) throws AdminNotFoundException;

    void deleteAdminById(Long id);

    AdminLoginResponse loginAdmin(AdminLoginRequest request);

    List<Book> searchBookByTitle(String title) throws BookNotFoundException;

    List<Book> findBooksByAuthorName(String firstname, String lastname);

    BookReservationResponse reserveBook(Long adminId, Long bookId) throws BookNotFoundException, BookNotAvailableException;

    void deleteAuthorById(Long authorId);

    void deleteBookById(Long bookId);

    void deleteUserById(Long userId);

    BookCheckoutResponse checkoutBook(Long bookId) throws BookNotFoundException, BookNotAvailableException;

    void returnBookAfterFiveDays(Admin admin, Book book);
}
