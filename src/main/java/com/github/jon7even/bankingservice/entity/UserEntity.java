package com.github.jon7even.bankingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@ToString
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

    @Column(name = "is_confirmed_phone")
    private Boolean isConfirmedPhone;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "register_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserEntity that)) {
            return false;
        }
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        eb.append(login, that.login);
        eb.append(email, that.email);
        eb.append(password, that.password);
        eb.append(phone, that.phone);
        eb.append(isConfirmedPhone, that.isConfirmedPhone);
        eb.append(firstName, that.firstName);
        eb.append(lastName, that.lastName);
        eb.append(middleName, that.middleName);
        eb.append(dateOfBirth, that.dateOfBirth);
        eb.append(registeredOn, that.registeredOn);
        eb.append(updatedOn, that.updatedOn);
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        hcb.append(login);
        hcb.append(email);
        hcb.append(password);
        hcb.append(phone);
        hcb.append(isConfirmedPhone);
        hcb.append(firstName);
        hcb.append(lastName);
        hcb.append(middleName);
        hcb.append(dateOfBirth);
        hcb.append(registeredOn);
        hcb.append(updatedOn);
        return hcb.toHashCode();
    }
}
