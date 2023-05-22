package com.remidiousE.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private String message;

    public boolean isLoggedIn() {
        AdminLoginResponse loginResponse = new AdminLoginResponse();
        boolean loggedIn = loginResponse.isLoggedIn();

        if (loggedIn) {
            return loginResponse.isLoggedIn();
        } else {
            return new AdminLoginResponse().isLoggedIn();
        }
    }
}
