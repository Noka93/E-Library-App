package com.remidiousE.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "book")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long adminId;
    private String title;

    @Enumerated()
    private Status status;
    private String description;
    private String isbn;
    private int year;
    private Long reservedBy;
    private LocalDate reservationTime;
    private Boolean setAvailable;
    private Boolean isAvailable;
    private int borrowedDays;
    private LocalDate borrowedDate;
    private String borrower;


    @OneToOne(fetch = FetchType.LAZY)
    private Author author;


    public void setAvailable(boolean b) {
    }

    public boolean isAvailable() {
        return true;
    }
}
