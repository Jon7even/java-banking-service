package com.github.jon7even.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.github.jon7even.constants.DateTimeFormat.DATE_DEFAULT;

/**
 * Класс DTO для регистрации нового пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String login;

    @ToString.Exclude
    private String password;

    private BankAccountCreateDto bankAccountCreateDto;

    @Builder.Default
    private Set<EmailCreateDto> emails = new HashSet<>();

    @Builder.Default
    private Set<PhoneCreateDto> phones = new HashSet<>();

    private String firstName;

    private String lastName;

    private String middleName;

    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;
}