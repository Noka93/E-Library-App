package com.remidiousE.dto.response;

import com.remidiousE.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegistrationResponse {
    private Long id;
    private String title;
    private String authorName;
    private Status status;
    private String description;

}
