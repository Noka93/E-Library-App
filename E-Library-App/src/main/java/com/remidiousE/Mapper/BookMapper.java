package com.remidiousE.Mapper;

import com.remidiousE.dto.request.BookSearchByTitleRequest;
import com.remidiousE.dto.request.BookRegistrationRequest;
import com.remidiousE.model.Book;

public class BookMapper {
    public static Book map(BookRegistrationRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthorName(request.getAuthorName());
        book.setStatus(request.getStatus());
        book.setDescription(request.getDescription());
        return book;
    }
    public static Book map(BookSearchByTitleRequest bookSearchByTitleRequest){
        BookSearchByTitleRequest searchByTitleRequest = new BookSearchByTitleRequest();
        Book book = new Book();
        book.setTitle(bookSearchByTitleRequest.getTitle());

        return book;
    }
}
