package com.github.jon7even.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @SequenceGenerator(name = "UserGenId", sequenceName = "application.user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenId")
    @Column(name = "id")
    private Long id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    private BankAccountEntity bankAccountEntity;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private final List<UserEmailEntity> emails = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private final List<UserPhoneEntity> phones = new ArrayList<>();

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
        hcb.append(firstName);
        hcb.append(lastName);
        hcb.append(middleName);
        hcb.append(dateOfBirth);
        hcb.append(registeredOn);
        hcb.append(updatedOn);
        return hcb.toHashCode();
    }
}
