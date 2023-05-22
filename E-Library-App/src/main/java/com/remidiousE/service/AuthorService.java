package com.remidiousE.service;

import com.remidiousE.Exceptions.AdminRegistrationException;
import com.remidiousE.Exceptions.AuthorRegistrationException;
import com.remidiousE.dto.request.AdminRegistrationRequest;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AdminLoginResponse;
import com.remidiousE.dto.response.AdminRegistrationResponse;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.model.Admin;
import com.remidiousE.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {

    AuthorRegistrationResponse registerNewAuthor(AuthorRegistrationRequest authorRequest) throws AuthorRegistrationException;

    Optional<Author> findAuthorById(Long id);

    List<Author> findAllAuthor();

    void deleteAuthorById(Long id);

}
