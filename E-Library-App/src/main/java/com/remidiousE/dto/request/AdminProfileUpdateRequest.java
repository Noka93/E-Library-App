package com.remidiousE.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdminProfileUpdateRequest {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
}


