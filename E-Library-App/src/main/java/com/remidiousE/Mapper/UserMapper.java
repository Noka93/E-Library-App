package com.remidiousE.Mapper;

import com.remidiousE.dto.request.UserRegistrationRequest;
import com.remidiousE.dto.response.UserRegistrationResponse;
import com.remidiousE.model.Book;
import com.remidiousE.model.User;

public class UserMapper {
    public static User map(UserRegistrationRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setHouseNumber(request.getHouseNumber());
        user.setStreet(request.getStreet());
        user.setLocalGovernmentArea(request.getLga());
        user.setState(request.getState());
        return user;
    }

    public static UserRegistrationResponse map(User request) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setId(request.getUserId());
        userRegistrationResponse.setMessage("Welcome!!! You have successfully registered");

        return userRegistrationResponse;

    }
}