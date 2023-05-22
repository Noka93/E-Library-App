package com.remidiousE.dto.request;

import com.remidiousE.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegistrationRequest {
    private String title;
    private String authorName;
    private Status status;
    private String description;

}
