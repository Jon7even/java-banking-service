package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
