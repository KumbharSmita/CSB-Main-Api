package com.example.accountapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "account")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank or null")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank or null")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Grant type cannot be blank or null")
    @Column(nullable = false, name = "grant_type")
    private String grantType;

    @NotBlank(message = "First name cannot be blank or null")
    @Column(nullable = false, name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank or null")
    @Column(nullable = false, name = "last_name")
    private String lastName;

    @NotBlank(message = "Email cannot be blank or null")
    @Column(nullable = false, name = "email")
    private String email;

    @NotBlank(message = "Mobile cannot be blank or null")
    @Column(nullable = false, name = "Mobile")
    private String mobile;

}
