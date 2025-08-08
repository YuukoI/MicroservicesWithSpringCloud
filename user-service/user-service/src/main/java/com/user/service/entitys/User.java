package com.user.service.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "dni is required")
    @Pattern(regexp = "\\d{8}", message = "DNI must have exactly 8 digits")
    private String dni;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "email is not valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;


}
