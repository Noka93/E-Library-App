package com.remidiousE.service;

import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.Mapper.AdminMapper;
import com.remidiousE.Mapper.BookMapper;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Address;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Book;
import com.remidiousE.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    @Override
    public BookRegistrationResponse registerNewBook(BookRegistrationRequest bookRequest) throws BookRegistrationException {
        Book newBook = BookMapper.map(bookRequest);
        bookRepository.save(newBook);

        BookRegistrationResponse bookRegistrationResponse = new BookRegistrationResponse();
        bookRegistrationResponse.setTitle(bookRegistrationResponse.getTitle());
        bookRegistrationResponse.setAuthorName(bookRequest.getAuthorName());
        bookRegistrationResponse.setStatus(bookRegistrationResponse.getStatus());
        bookRegistrationResponse.setDescription(bookRegistrationResponse.getDescription());

        return bookRegistrationResponse;
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        Optional<Book>foundBook = bookRepository.findById(id);
        return foundBook;
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public AdminLoginResponse loginAdmin(AdminRegistrationRequest request) {

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();

        String username = request.getUsername();
        String password = request.getPassword();

        if (authenticate(username, password)) {
            adminLoginResponse.setMessage("You have logged in successfully");
        } else {
            adminLoginResponse.setMessage("Failed to log in");
        }

        return adminLoginResponse;
    }
    private boolean authenticate(String username, String password) {
        if (username.equals("admin") && password.equals("password")) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Book> searchBookByTitle(String title) {
        List<Book> searchResults = bookRepository.searchByTitle(title);
        return searchResults;
    }
}
