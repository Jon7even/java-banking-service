package com.github.jon7even.bankingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @SequenceGenerator(name = "UserGenId", sequenceName = "application.user_seq", allocationSize = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenId")
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<UserEmailEntity> emails = new HashSet<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<UserPhoneEntity> phones = new HashSet<>();

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
        eb.append(password, that.password);
        eb.append(emails, that.emails);
        eb.append(phones, that.phones);
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
        hcb.append(password);
        hcb.append(emails);
        hcb.append(phones);
        hcb.append(firstName);
        hcb.append(lastName);
        hcb.append(middleName);
        hcb.append(dateOfBirth);
        hcb.append(registeredOn);
        hcb.append(updatedOn);
        return hcb.toHashCode();
    }
}
