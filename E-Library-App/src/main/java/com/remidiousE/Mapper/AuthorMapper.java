package com.remidiousE.Mapper;

import com.remidiousE.dto.request.AuthorRegistrationRequest;
import com.remidiousE.model.Author;

public class AuthorMapper {
    public static Author map(AuthorRegistrationRequest authorRegistrationRequest) {
        Author author = new Author();
        author.setFirstName(authorRegistrationRequest.getFirstName());
        author.setLastName(authorRegistrationRequest.getLastName());
        author.setEmail(authorRegistrationRequest.getEmail());
        author.setHouseNumber(authorRegistrationRequest.getHouseNumber());
        author.setStreet(authorRegistrationRequest.getStreet());
        author.setTown(authorRegistrationRequest.getTown());
        author.setState(authorRegistrationRequest.getState());
        return author;
    }

}
