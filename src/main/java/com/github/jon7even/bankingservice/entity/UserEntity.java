package com.github.jon7even.bankingservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
@Table(name = "user", schema = "application")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "UserGenId", sequenceName = "user_seq", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenId")
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "register_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id.equals(that.id)
                && password.equals(that.password)
                && email.equals(that.email)
                && firstName.equals(that.firstName)
                && lastName.equals(that.lastName)
                && login.equals(that.login)
                && phone.equals(that.phone)
                && dateOfBirth.equals(that.dateOfBirth)
                && registeredOn.equals(that.registeredOn)
                && updatedOn.equals(that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, password, email, firstName, lastName, login, phone, dateOfBirth, registeredOn, updatedOn
        );
    }
}
