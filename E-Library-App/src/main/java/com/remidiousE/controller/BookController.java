package com.remidiousE.controller;

import com.remidiousE.Exceptions.BookNotFoundException;
import com.remidiousE.Exceptions.BookRegistrationException;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Book;
import com.remidiousE.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("api/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create-book")
    public ResponseEntity<BookRegistrationResponse> registerNewBook(@RequestBody BookRegistrationRequest bookRegistrationRequest) throws BookRegistrationException {
        BookRegistrationResponse response = bookService.registerNewBook(bookRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find-book/{id}")
    public Optional<Book> findBookById(@PathVariable("id") Long bookId) throws BookNotFoundException {
         return bookService.findBookById(bookId);
    }

    @GetMapping("/get-books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @PutMapping("/book-update-book/{id}")
    public Book updateBookById(@PathVariable("id") Long bookId, @RequestBody Book book) throws BookNotFoundException {
        return bookService.updateBookById(bookId, book);
    }

    @GetMapping("/user-book-title/{title}")
    public List<Book> searchBookByTitle(@PathVariable("title") String title) throws BookNotFoundException {
        return bookService.searchBookByTitle(title);
    }

    @DeleteMapping("/delete-book/{bookId}")
    public String deleteBookById(@PathVariable("bookId") Long bookId) throws BookNotFoundException{
        bookService.deleteBookById(bookId);
        return "Book has been deleted successfully";
    }

}
