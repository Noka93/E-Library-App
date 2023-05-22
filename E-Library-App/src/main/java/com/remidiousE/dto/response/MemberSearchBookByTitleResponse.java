package com.remidiousE.dto.response;

import com.remidiousE.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberSearchBookByTitleResponse {
    private String title;
    private Status status;
    private String description;
}
