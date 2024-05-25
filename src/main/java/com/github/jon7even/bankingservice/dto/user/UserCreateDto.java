package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.bankingservice.dto.user.email.EmailCreateDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneCreateDto;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;

/**
 * Класс DTO для регистрации нового пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private String login;

    @ToString.Exclude
    private String password;

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