package com.github.jon7even.bankingservice.setup;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailCreateDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.bankingservice.entity.UserEmailEntity;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.entity.UserPhoneEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class PreparationObjectsForTests {
    protected Long firstId = 1L;
    protected Long secondId = 2L;
    protected Long thirdId = 3L;
    protected Long fourthId = 4L;
    protected Long fifthId = 5L;
    protected Long sixthId = 6L;
    protected Long seventhId = 7L;

    protected UserEntity userEntityFirst;
    protected UserEntity userEntityFirstWithoutId;
    protected Set<UserEmailEntity> setUserEmailEntitiesFirst;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesFirst;
    protected Set<UserEmailEntity> setUserEmailEntitiesFirstWithoutId;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesFirstWithoutId;

    protected UserEntity userEntitySecond;
    protected UserEntity userEntitySecondWithoutId;
    protected Set<UserEmailEntity> setUserEmailEntitiesSecond;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesSecond;
    protected Set<UserEmailEntity> setUserEmailEntitiesSecondWithoutId;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesSecondWithoutId;


    protected UserEntity userEntityThird;
    protected UserEntity userEntityThirdWithoutId;
    protected Set<UserEmailEntity> setUserEmailEntitiesThird;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesThird;
    protected Set<UserEmailEntity> setUserEmailEntitiesThirdWithoutId;
    protected Set<UserPhoneEntity> setUserPhoneEntitiesThirdWithoutId;

    protected UserCreateDto userCreateDtoFirst;
    protected Set<EmailCreateDto> setEmailCreateDtoFirst;
    protected Set<PhoneCreateDto> setPhoneCreateDtoFirst;

    protected UserCreateDto userCreateDtoSecond;
    protected Set<EmailCreateDto> setEmailCreateDtoSecond;
    protected Set<PhoneCreateDto> setPhoneCreateDtoSecond;

    protected UserCreateDto userCreateDtoThird;
    protected Set<EmailCreateDto> setEmailCreateDtoThird;
    protected Set<PhoneCreateDto> setPhoneCreateDtoThird;

    protected UserFullResponseDto userFullResponseDtoFirst;
    protected Set<EmailShortResponseDto> emailShortResponseDtoFirst;
    protected Set<PhoneShortResponseDto> phoneShortResponseDtoFirst;

    protected UserFullResponseDto userFullResponseDtoSecond;
    protected Set<EmailShortResponseDto> emailShortResponseDtoSecond;
    protected Set<PhoneShortResponseDto> phoneShortResponseDtoSecond;

    protected UserFullResponseDto userFullResponseDtoThird;
    protected Set<EmailShortResponseDto> emailShortResponseDtoThird;
    protected Set<PhoneShortResponseDto> phoneShortResponseDtoThird;

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
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        setUserEmailEntitiesFirst = Set.of(UserEmailEntity.builder()
                .id(firstId).email("First@email.ru").owner(userEntityFirst)
                .build());

        setUserPhoneEntitiesFirst = Set.of(UserPhoneEntity.builder()
                .id(firstId).phone("+79000000001").owner(userEntityFirst)
                .build());

        userEntityFirstWithoutId = UserEntity.builder()
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        setUserEmailEntitiesFirstWithoutId = Set.of(UserEmailEntity.builder()
                .email("First@email.ru").owner(userEntityFirst)
                .build());

        setUserPhoneEntitiesFirstWithoutId = Set.of(UserPhoneEntity.builder()
                .phone("+79000000001").owner(userEntityFirst)
                .build());

        userEntitySecond = UserEntity.builder()
                .id(secondId)
                .login("SecondLogin")
                .password("SecondPassword")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(secondDateTime)
                .build();

        setUserEmailEntitiesSecond = Set.of(
                UserEmailEntity.builder()
                        .id(firstId).email("Second1@email.ru").owner(userEntitySecond)
                        .build(),
                UserEmailEntity.builder()
                        .id(secondId).email("Second2@email.ru").owner(userEntitySecond)
                        .build());

        setUserPhoneEntitiesSecond = Set.of(
                UserPhoneEntity.builder()
                        .id(firstId).phone("+79000000002").owner(userEntitySecond)
                        .build(),
                UserPhoneEntity.builder()
                        .id(secondId).phone("+79000000022").owner(userEntitySecond)
                        .build());

        userEntitySecondWithoutId = UserEntity.builder()
                .login("SecondLogin")
                .password("SecondPassword")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(secondDateTime)
                .build();

        setUserEmailEntitiesSecondWithoutId = Set.of(
                UserEmailEntity.builder()
                        .email("Second1@email.ru").owner(userEntitySecondWithoutId)
                        .build(),
                UserEmailEntity.builder()
                        .email("Second2@email.ru").owner(userEntitySecondWithoutId)
                        .build());

        setUserPhoneEntitiesSecondWithoutId = Set.of(
                UserPhoneEntity.builder()
                        .phone("+79000000002").owner(userEntitySecondWithoutId)
                        .build(),
                UserPhoneEntity.builder()
                        .phone("+79000000022").owner(userEntitySecondWithoutId)
                        .build());

        userEntityThird = UserEntity.builder()
                .id(thirdId)
                .login("ThirdLogin")
                .password("ThirdPassword")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .registeredOn(thirdDateTime)
                .build();

        setUserEmailEntitiesThird = Set.of(
                UserEmailEntity.builder()
                        .id(firstId).email("Third1@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .id(secondId).email("Third2@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .id(thirdId).email("Third3@email.ru").owner(userEntityThird)
                        .build());

        setUserPhoneEntitiesThird = Set.of(
                UserPhoneEntity.builder()
                        .id(firstId).phone("+79000000003").owner(userEntityThird)
                        .build(),
                UserPhoneEntity.builder()
                        .id(secondId).phone("+79000000032").owner(userEntityThird)
                        .build(),
                UserPhoneEntity.builder()
                        .id(thirdId).phone("+79000000033").owner(userEntityThird)
                        .build());

        userEntityThirdWithoutId = UserEntity.builder()
                .login("ThirdLogin")
                .password("ThirdPassword")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .registeredOn(thirdDateTime)
                .build();

        setUserEmailEntitiesThirdWithoutId = Set.of(
                UserEmailEntity.builder()
                        .email("Third1@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .email("Third2@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .email("Third3@email.ru").owner(userEntityThird)
                        .build());

        setUserPhoneEntitiesThirdWithoutId = Set.of(
                UserPhoneEntity.builder()
                        .phone("+79000000003").owner(userEntityThird)
                        .build(),
                UserPhoneEntity.builder()
                        .phone("+79000000032").owner(userEntityThird)
                        .build(),
                UserPhoneEntity.builder()
                        .phone("+79000000033").owner(userEntityThird)
                        .build());
    }

    protected void initUserCreateDto() {
        userCreateDtoFirst = UserCreateDto.builder()
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .build();

        setEmailCreateDtoFirst = Set.of(EmailCreateDto.builder().email("First@email.ru").build());

        setPhoneCreateDtoFirst = Set.of(PhoneCreateDto.builder().phone("+79000000001").build());

        userCreateDtoSecond = UserCreateDto.builder()
                .login("SecondLogin")
                .password("SecondPassword")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .build();

        setEmailCreateDtoSecond = Set.of(
                EmailCreateDto.builder()
                        .email("Second1@email.ru")
                        .build(),
                EmailCreateDto.builder()
                        .email("Second2@email.ru")
                        .build());

        setPhoneCreateDtoSecond = Set.of(
                PhoneCreateDto.builder()
                        .phone("+79000000002")
                        .build(),
                PhoneCreateDto.builder()
                        .phone("+79000000022")
                        .build());

        userCreateDtoThird = UserCreateDto.builder()
                .login("ThirdLogin")
                .password("ThirdPassword")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .build();

        setEmailCreateDtoThird = Set.of(
                EmailCreateDto.builder()
                        .email("Third1@email.ru")
                        .build(),
                EmailCreateDto.builder()
                        .email("Third2@email.ru")
                        .build(),
                EmailCreateDto.builder()
                        .email("Third3@email.ru")
                        .build());

        setPhoneCreateDtoThird = Set.of(
                PhoneCreateDto.builder()
                        .phone("+79000000003")
                        .build(),
                PhoneCreateDto.builder()
                        .phone("+79000000032")
                        .build(),
                PhoneCreateDto.builder()
                        .phone("+79000000033")
                        .build());
    }

    protected void initUserFullResponseDto() {
        userFullResponseDtoFirst = UserFullResponseDto.builder()
                .id(firstId)
                .login("FirstLogin")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .updatedOn(null)
                .build();

        emailShortResponseDtoFirst = Set.of(EmailShortResponseDto.builder().email("First@email.ru").build());

        phoneShortResponseDtoFirst = Set.of(PhoneShortResponseDto.builder().phone("+79000000001").build());

        userFullResponseDtoSecond = UserFullResponseDto.builder()
                .id(secondId)
                .login("SecondLogin")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(secondDateTime)
                .updatedOn(null)
                .build();

        emailShortResponseDtoSecond = Set.of(
                EmailShortResponseDto.builder()
                        .email("Second1@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Second2@email.ru")
                        .build());

        phoneShortResponseDtoSecond = Set.of(
                PhoneShortResponseDto.builder()
                        .phone("+79000000002")
                        .build(),
                PhoneShortResponseDto.builder()
                        .phone("+79000000022")
                        .build());

        userFullResponseDtoThird = UserFullResponseDto.builder()
                .id(thirdId)
                .login("ThirdLogin")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .registeredOn(thirdDateTime)
                .updatedOn(null)
                .build();

        emailShortResponseDtoThird = Set.of(
                EmailShortResponseDto.builder()
                        .email("Third1@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Third2@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Third3@email.ru")
                        .build());

        phoneShortResponseDtoThird = Set.of(
                PhoneShortResponseDto.builder()
                        .phone("+79000000003")
                        .build(),
                PhoneShortResponseDto.builder()
                        .phone("+79000000032")
                        .build(),
                PhoneShortResponseDto.builder()
                        .phone("+79000000033")
                        .build());
    }
}