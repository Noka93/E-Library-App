package com.remidiousE.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookCheckoutResponse {
    private Long bookId;
    private String title;
    private String checkedOutBy;
}
