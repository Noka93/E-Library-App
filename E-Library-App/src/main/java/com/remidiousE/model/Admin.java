package com.remidiousE.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    @Column(name = "role_table")
    private String role;
    private String houseNumber;
    private String street;
    private String localGovernmentArea;
    private String state;

    @OneToOne(mappedBy = "admin")
    private User user;

}
