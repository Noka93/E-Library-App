package com.remidiousE.dto.response;

import com.remidiousE.model.Author;
import com.remidiousE.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookRegistrationResponse {
    private Long id;
    private String authorName;
    private String title;
    private String description;
    private int year;
    private String message;
}
