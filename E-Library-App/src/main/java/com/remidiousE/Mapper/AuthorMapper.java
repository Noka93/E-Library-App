package com.remidiousE.Mapper;

import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.dto.response.AuthorRegistrationResponse;
import com.remidiousE.model.Author;

public class AuthorMapper {
    public static Author map(AuthorRegistrationRequest authorRegistrationRequest) {
        Author author = new Author();
        author.setFirstName(authorRegistrationRequest.getFirstName());
        author.setLastName(authorRegistrationRequest.getLastName());
        author.setUsername(authorRegistrationRequest.getUsername());
        author.setEmail(authorRegistrationRequest.getEmail());
        author.setPhoneNumber(authorRegistrationRequest.getPhoneNumber());
        author.setHouseNumber(authorRegistrationRequest.getHouseNumber());
        author.setLga(authorRegistrationRequest.getLga());
        author.setStreet(authorRegistrationRequest.getStreet());
        author.setState(authorRegistrationRequest.getState());
        return author;
    }

    public static AuthorRegistrationResponse map(Author author){
        AuthorRegistrationResponse authorRegistrationResponse = new AuthorRegistrationResponse();
        authorRegistrationResponse.setId(author.getId());
        authorRegistrationResponse.setMessage("Welcome!!! You have successfully registered");

        return authorRegistrationResponse;

    }

}
