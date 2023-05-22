package com.remidiousE.service;

import com.remidiousE.Exceptions.AuthorRegistrationException;
import com.remidiousE.Mapper.AdminMapper;
import com.remidiousE.Mapper.AuthorMapper;
import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.model.Author;
import com.remidiousE.repositories.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;


    @Override
    public AuthorRegistrationResponse registerNewAuthor(AuthorRegistrationRequest authorRequest) throws AuthorRegistrationException {

        Author newAuthor = AuthorMapper.map(authorRequest);

        authorRepository.save(newAuthor);

        AuthorRegistrationResponse authorResponse = new AuthorRegistrationResponse();
        authorResponse.setMessage("Your have successfully Registered");

        return authorResponse;
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        Optional<Author> foundAuthor = authorRepository.findById(id);
        return foundAuthor;
    }

    @Override
    public List<Author> findAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);

    }
}
