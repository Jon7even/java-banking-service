package com.github.jon7even.bankingservice.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

/**
 * Класс описывающий сущность пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String phone;
    private LocalDate dateOfBirth;
}
