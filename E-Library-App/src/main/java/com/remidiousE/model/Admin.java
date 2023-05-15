package com.remidiousE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;

    @OneToMany(mappedBy = "admin")
    private List<Address> addresses;

}
