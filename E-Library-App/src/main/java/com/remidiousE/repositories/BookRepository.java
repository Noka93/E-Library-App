package com.remidiousE.repositories;

import com.remidiousE.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> searchBookByTitle(String title);
    List<Book> findBookByAuthor_FirstNameAndAuthor_LastName(String author_firstName, String author_lastName);

}
