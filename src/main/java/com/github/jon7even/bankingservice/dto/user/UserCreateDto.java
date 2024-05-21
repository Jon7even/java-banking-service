package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String email;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;
}