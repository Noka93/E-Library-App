package com.remidiousE.repositories;

import com.remidiousE.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findAuthorByUsername(String username);
    Boolean existsAuthorByUsername(String username);

    Optional<Author> findByFirstName(String firstName);
}
