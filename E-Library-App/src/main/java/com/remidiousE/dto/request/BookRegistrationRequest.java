package com.remidiousE.dto.request;

import com.remidiousE.model.Author;
import com.remidiousE.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegistrationRequest {
        private String title;
        private String isbn;
        private String description;
        private int year;
        private String authorName;
}
