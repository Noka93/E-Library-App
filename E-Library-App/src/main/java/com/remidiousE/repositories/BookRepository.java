package com.remidiousE.repositories;

import com.remidiousE.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> searchByTitle(String title);
}
