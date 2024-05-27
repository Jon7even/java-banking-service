package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.bankingservice.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneShortResponseDto;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;
import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_TIME_DEFAULT;

/**
 * Класс DTO для полного представления информации о пользователе
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFullResponseDto {
    private Long id;

    private String login;

    private List<EmailShortResponseDto> emails;

    private List<PhoneShortResponseDto> phones;

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
        eb.append(id, that.id);
        eb.append(login, that.login);
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
