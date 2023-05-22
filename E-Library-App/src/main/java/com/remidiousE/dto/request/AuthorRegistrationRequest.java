package com.remidiousE.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRegistrationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String houseNumber;
    private String street;
    private String town;
    private String lga;
    private String state;
}
