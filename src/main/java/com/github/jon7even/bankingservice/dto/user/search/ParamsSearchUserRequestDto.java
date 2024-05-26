package com.github.jon7even.bankingservice.dto.user.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;

/**
 * Класс DTO для поиска пользователей по параметрам
 *
 * @author Jon7even
 * @version 1.0
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParamsSearchUserRequestDto {
    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;

    private String phone;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    private UserSort sort;

    private Integer from;

    private Integer size;
}
