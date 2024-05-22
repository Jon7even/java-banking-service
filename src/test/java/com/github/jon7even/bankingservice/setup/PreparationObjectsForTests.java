package com.github.jon7even.bankingservice.setup;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PreparationObjectsForTests {
    protected Long firstId = 1L;
    protected Long secondId = 2L;
    protected Long thirdId = 3L;
    protected Long fourthId = 4L;
    protected Long fifthId = 5L;
    protected Long sixthId = 6L;
    protected Long seventhId = 7L;

    protected UserEntity userEntityFirst;
    protected UserEntity userEntitySecond;
    protected UserEntity userEntityThird;

    protected UserCreateDto userCreateDtoFirst;
    protected UserCreateDto userCreateDtoSecond;
    protected UserCreateDto userCreateDtoThird;

    protected UserFullResponseDto userFullResponseDtoFirst;
    protected UserFullResponseDto userFullResponseDtoSecond;
    protected UserFullResponseDto userFullResponseDtoThird;

    protected LocalDate firstDate = LocalDate.of(1990, 1, 1);
    protected LocalDate secondDate = LocalDate.of(1985, 4, 25);
    protected LocalDate thirdDate = LocalDate.of(1980, 2, 17);

    protected LocalDateTime firstDateTime = LocalDateTime.of(2024, 5, 18, 10, 0);
    protected LocalDateTime secondDateTime = LocalDateTime.of(2024, 5, 17, 11, 20);
    protected LocalDateTime thirdDateTime = LocalDateTime.of(2024, 5, 16, 17, 17);

    protected void initUserEntity() {
        userEntityFirst = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .email("First@email.ru")
                .password("FirstPassword")
                .phone("+79000000001")
                .isConfirmedPhone(false)
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        userEntitySecond = UserEntity.builder()
                .id(secondId)
                .login("SecondLogin")
                .email("Second@email.ru")
                .password("SecondPassword")
                .phone("+79000000002")
                .isConfirmedPhone(false)
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(secondDateTime)
                .build();

        userEntityThird = UserEntity.builder()
                .id(thirdId)
                .login("ThirdLogin")
                .email("Third@email.ru")
                .password("ThirdPassword")
                .phone("+79000000003")
                .isConfirmedPhone(false)
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .registeredOn(thirdDateTime)
                .build();
    }

    protected void initUserCreateDto() {
        userCreateDtoFirst = UserCreateDto.builder()
                .login("FirstLogin")
                .email("First@email.ru")
                .password("FirstPassword")
                .phone("+79000000001")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .build();

        userCreateDtoSecond = UserCreateDto.builder()
                .login("SecondLogin")
                .email("Second@email.ru")
                .password("SecondPassword")
                .phone("+79000000002")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .build();

        userCreateDtoThird = UserCreateDto.builder()
                .login("ThirdLogin")
                .email("Third@email.ru")
                .password("ThirdPassword")
                .phone("+79000000003")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .build();
    }

    protected void initUserFullResponseDto() {
        userFullResponseDtoFirst = UserFullResponseDto.builder()
                .id(firstId)
                .login("FirstLogin")
                .email("First@email.ru")
                .phone("+79000000001")
                .isConfirmedPhone(false)
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .updatedOn(null)
                .build();

        userFullResponseDtoSecond = UserFullResponseDto.builder()
                .id(secondId)
                .login("SecondLogin")
                .email("Second@email.ru")
                .phone("+79000000002")
                .isConfirmedPhone(false)
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(secondDateTime)
                .updatedOn(null)
                .build();

        userFullResponseDtoThird = UserFullResponseDto.builder()
                .id(thirdId)
                .login("ThirdLogin")
                .email("Third@email.ru")
                .phone("+79000000003")
                .isConfirmedPhone(false)
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .registeredOn(thirdDateTime)
                .updatedOn(null)
                .build();
    }
}