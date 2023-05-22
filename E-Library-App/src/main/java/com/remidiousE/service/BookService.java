package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    BookRegistrationResponse registerNewBook(BookRegistrationRequest bookRequest) throws BookRegistrationException;

    Optional<Book> findBookById(Long id);

    List<Book> findAllBooks();

    void deleteBookById(Long id);

    AdminLoginResponse loginAdmin(AdminRegistrationRequest request);

    List<Book> searchBookByTitle(String title);
}
