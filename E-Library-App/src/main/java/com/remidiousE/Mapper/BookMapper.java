package com.remidiousE.Mapper;

import com.remidiousE.dto.request.BookCheckOutRequest;
import com.remidiousE.dto.request.BookReservationRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.dto.response.BookRegistrationResponse;
import com.remidiousE.model.Author;
import com.remidiousE.model.Book;
import com.remidiousE.model.Status;

public class BookMapper {
    public static Book map(BookRegistrationRequest request) {
        Book book = new Book();
        Author author = buildAuthor();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setDescription(request.getDescription());
        book.setYear(request.getYear());
        book.setAuthor(author);
        return book;
    }

    public static BookRegistrationResponse map(Book book) {
        BookRegistrationResponse bookRegistrationResponse = new BookRegistrationResponse();
        Author author = book.getAuthor();
        bookRegistrationResponse.setAuthorName(author.getFirstName());
        bookRegistrationResponse.setTitle(book.getTitle());
        bookRegistrationResponse.setDescription(book.getDescription());
        bookRegistrationResponse.setYear(book.getYear());
        bookRegistrationResponse.setMessage("You have successfully registered the book with the details above");
        return bookRegistrationResponse;
    }

    public static Author buildAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        return author;
    }

    public static Book map(BookReservationRequest bookReservationRequest) {
        Book book = new Book();
        book.setStatus(Status.AVAILABLE);
        book.setReservationTime(bookReservationRequest.getDateTime().toLocalDate());
        book.setReservedBy(bookReservationRequest.getReservedBy());
        return book;
    }

    public static Book map(BookCheckOutRequest bookCheckOutRequest) {
        Book book = new Book();
        book.setTitle(bookCheckOutRequest.getTitle());
        return book;
    }
}
