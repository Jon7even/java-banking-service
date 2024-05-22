package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;
import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_TIME_DEFAULT;

/**
 * Класс DTO для полного представления информации о пользователе
 *
 * @author Jon7even
 * @version 1.0
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class UserFullResponseDto {
    private Long id;

    private String login;

    private String email;

    private String phone;

    private boolean isConfirmedPhone;

    private String firstName;

    private String lastName;

    private String middleName;

    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private LocalDateTime registeredOn;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private LocalDateTime updatedOn;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserFullResponseDto that)) {
            return false;
        }
        EqualsBuilder eb = new EqualsBuilder();
        if (that.id != null) {
            eb.append(id, that.id);
        }
        eb.append(login, that.login);
        eb.append(email, that.email);
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
