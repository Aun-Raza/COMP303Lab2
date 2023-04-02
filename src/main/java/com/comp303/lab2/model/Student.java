package com.comp303.lab2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentId;

    @NotBlank
    @Size(min = 5, message = "Username should be at least 5 characters")
    private String username;

    @NotBlank
    @Size(min = 5, message = "Password should be at least 5 characters")
    private String password;

    @NotBlank
    @Size(min = 2, message = "First Name should be at least 2 characters")
    private String firstName;

    @NotBlank
    @Size(min = 2, message = "Last Name should be at least 2 characters")
    private String lastName;

    @NotBlank
    @Size(min = 5, message = "Address should be at least 5 characters")
    private String address;

    @NotBlank
    @Size(min = 2, message = "City should be at least 2 characters")
    private String city;

    @NotBlank
    @Size(min = 5, message = "Postal Code should be at least 5 characters")
    private String postalCode;
}
