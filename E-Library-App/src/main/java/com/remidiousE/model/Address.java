package com.remidiousE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String houseNumber;
    private  String street;
    private  String localGovernmentArea;
    private  String state;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
