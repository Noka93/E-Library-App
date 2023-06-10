package com.remidiousE.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookReservationRequest {
    private Long reservedBy;
    private LocalDateTime dateTime;
}
