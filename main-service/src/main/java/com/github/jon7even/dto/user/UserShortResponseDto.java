package com.github.jon7even.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.constants.DateTimeFormat;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс DTO для краткого представления информации о пользователе
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortResponseDto {
    private Long id;

    private String login;

    private BankAccountShortResponseDto bankAccount;

    private String firstName;

    private String lastName;

    private String middleName;

    @JsonFormat(pattern = DateTimeFormat.DATE_DEFAULT)
    private LocalDate dateOfBirth;

    private List<EmailShortResponseDto> emails;

    private List<PhoneShortResponseDto> phones;
}
