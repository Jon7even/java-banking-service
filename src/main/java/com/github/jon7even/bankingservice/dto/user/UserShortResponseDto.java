package com.github.jon7even.bankingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.bankingservice.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneShortResponseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;

/**
 * Класс DTO для краткого представления информации о пользователе
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
public class UserShortResponseDto {
    private String login;

    private String firstName;

    private String lastName;

    private String middleName;

    @JsonFormat(pattern = DATE_DEFAULT)
    private LocalDate dateOfBirth;

    private List<EmailShortResponseDto> emails;

    private List<PhoneShortResponseDto> phones;
}
