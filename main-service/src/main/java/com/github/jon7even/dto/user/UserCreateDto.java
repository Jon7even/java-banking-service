package com.github.jon7even.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Поле [login] не может быть пуcтым")
    private String login;

    @NotBlank(message = "Поле [password] не может быть пуcтым")
    @ToString.Exclude
    private String password;

    private BankAccountCreateDto bankAccount;

    @Builder.Default
    private Set<EmailCreateDto> emails = new HashSet<>();

    @Builder.Default
    private Set<PhoneCreateDto> phones = new HashSet<>();

    @NotBlank(message = "Поле [firstName] не может быть пуcтым")
    private String firstName;

    @NotBlank(message = "Поле [lastName] не может быть пуcтым")
    private String lastName;

    @NotBlank(message = "Поле [middleName] не может быть пуcтым")
    private String middleName;

    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;
}