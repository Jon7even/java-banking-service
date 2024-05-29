package com.github.jon7even.setup;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.UserShortResponseDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.entity.UserPhoneEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    protected List<UserEmailEntity> listUserEmailEntitiesFirst;
    protected List<UserPhoneEntity> listUserPhoneEntitiesFirst;
    protected BankAccountEntity bankAccountEntityFirst;
    protected List<UserEmailEntity> listUserEmailEntitiesFirstWithoutId;
    protected List<UserPhoneEntity> listUserPhoneEntitiesFirstWithoutId;
    protected BankAccountEntity bankAccountEntityFirstWithoutId;

    protected UserEntity userEntitySecond;
    protected UserEntity userEntitySecondWithoutId;
    protected BankAccountEntity bankAccountEntitySecond;
    protected List<UserEmailEntity> listUserEmailEntitiesSecond;
    protected List<UserPhoneEntity> listUserPhoneEntitiesSecond;
    protected BankAccountEntity bankAccountEntitySecondWithoutId;
    protected List<UserEmailEntity> listUserEmailEntitiesSecondWithoutId;
    protected List<UserPhoneEntity> listUserPhoneEntitiesSecondWithoutId;

    protected UserEntity userEntityThird;
    protected UserEntity userEntityThirdWithoutId;
    protected BankAccountEntity bankAccountEntityThird;
    protected List<UserEmailEntity> listUserEmailEntitiesThird;
    protected List<UserPhoneEntity> listUserPhoneEntitiesThird;
    protected BankAccountEntity bankAccountEntityThirdWithoutId;
    protected List<UserEmailEntity> listUserEmailEntitiesThirdWithoutId;
    protected List<UserPhoneEntity> listUserPhoneEntitiesThirdWithoutId;

    protected UserCreateDto userCreateDtoFirst;
    protected BankAccountCreateDto bankAccountCreateDtoFirst;
    protected Set<EmailCreateDto> setEmailCreateDtoFirst;
    protected Set<PhoneCreateDto> setPhoneCreateDtoFirst;

    protected UserCreateDto userCreateDtoSecond;
    protected BankAccountCreateDto bankAccountCreateDtoSecond;
    protected Set<EmailCreateDto> setEmailCreateDtoSecond;
    protected Set<PhoneCreateDto> setPhoneCreateDtoSecond;

    protected UserCreateDto userCreateDtoThird;
    protected BankAccountCreateDto bankAccountCreateDtoThird;
    protected Set<EmailCreateDto> setEmailCreateDtoThird;
    protected Set<PhoneCreateDto> setPhoneCreateDtoThird;

    protected UserFullResponseDto userFullResponseDtoFirst;
    protected UserShortResponseDto userShortResponseDtoFirst;
    protected List<EmailShortResponseDto> emailShortResponseDtoFirst;
    protected List<PhoneShortResponseDto> phoneShortResponseDtoFirst;
    protected BankAccountShortResponseDto bankAccountShortResponseDto;

    protected UserFullResponseDto userFullResponseDtoSecond;
    protected List<EmailShortResponseDto> emailShortResponseDtoSecond;
    protected List<PhoneShortResponseDto> phoneShortResponseDtoSecond;

    protected UserFullResponseDto userFullResponseDtoThird;
    protected List<EmailShortResponseDto> emailShortResponseDtoThird;
    protected List<PhoneShortResponseDto> phoneShortResponseDtoThird;

    protected LocalDate firstDate = LocalDate.of(1990, 1, 1);
    protected LocalDate secondDate = LocalDate.of(1985, 4, 25);
    protected LocalDate thirdDate = LocalDate.of(1980, 2, 17);

    protected LocalDateTime firstDateTime = LocalDateTime.of(2024, 5, 18, 10, 0);
    protected LocalDateTime secondDateTime = LocalDateTime.of(2024, 5, 17, 11, 20);
    protected LocalDateTime thirdDateTime = LocalDateTime.of(2024, 5, 16, 17, 17);

    protected BigDecimal balanceFirst = BigDecimal.valueOf(50000);
    protected BigDecimal balanceSecond = BigDecimal.valueOf(70000);
    protected BigDecimal balanceThird = BigDecimal.valueOf(100000);

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

        bankAccountEntityFirst = BankAccountEntity.builder()
                .id(firstId)
                .balance(balanceFirst)
                .owner(userEntityFirst)
                .build();

        listUserEmailEntitiesFirst = List.of(UserEmailEntity.builder()
                .id(firstId).email("First@email.ru").owner(userEntityFirst)
                .build());

        listUserPhoneEntitiesFirst = List.of(UserPhoneEntity.builder()
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

        bankAccountEntityFirstWithoutId = BankAccountEntity.builder()
                .balance(balanceFirst)
                .owner(userEntityFirst)
                .build();

        listUserEmailEntitiesFirstWithoutId = List.of(UserEmailEntity.builder()
                .email("First@email.ru").owner(userEntityFirst)
                .build());

        listUserPhoneEntitiesFirstWithoutId = List.of(UserPhoneEntity.builder()
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

        bankAccountEntitySecond = BankAccountEntity.builder()
                .id(secondId)
                .balance(balanceSecond)
                .owner(userEntitySecond)
                .build();

        listUserEmailEntitiesSecond = List.of(
                UserEmailEntity.builder()
                        .id(firstId).email("Second1@email.ru").owner(userEntitySecond)
                        .build(),
                UserEmailEntity.builder()
                        .id(secondId).email("Second2@email.ru").owner(userEntitySecond)
                        .build());

        listUserPhoneEntitiesSecond = List.of(
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

        bankAccountEntitySecondWithoutId = BankAccountEntity.builder()
                .balance(balanceSecond)
                .owner(userEntitySecond)
                .build();

        listUserEmailEntitiesSecondWithoutId = List.of(
                UserEmailEntity.builder()
                        .email("Second1@email.ru").owner(userEntitySecondWithoutId)
                        .build(),
                UserEmailEntity.builder()
                        .email("Second2@email.ru").owner(userEntitySecondWithoutId)
                        .build());

        listUserPhoneEntitiesSecondWithoutId = List.of(
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

        bankAccountEntityThird = BankAccountEntity.builder()
                .id(thirdId)
                .balance(balanceThird)
                .owner(userEntityThird)
                .build();

        listUserEmailEntitiesThird = List.of(
                UserEmailEntity.builder()
                        .id(firstId).email("Third1@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .id(secondId).email("Third2@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .id(thirdId).email("Third3@email.ru").owner(userEntityThird)
                        .build());

        listUserPhoneEntitiesThird = List.of(
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

        bankAccountEntityThirdWithoutId = BankAccountEntity.builder()
                .balance(balanceThird)
                .owner(userEntityThird)
                .build();

        listUserEmailEntitiesThirdWithoutId = List.of(
                UserEmailEntity.builder()
                        .email("Third1@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .email("Third2@email.ru").owner(userEntityThird)
                        .build(),
                UserEmailEntity.builder()
                        .email("Third3@email.ru").owner(userEntityThird)
                        .build());

        listUserPhoneEntitiesThirdWithoutId = List.of(
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

        bankAccountCreateDtoFirst = BankAccountCreateDto.builder().balance(balanceFirst).build();

        userCreateDtoFirst.setEmails(setEmailCreateDtoFirst);
        userCreateDtoFirst.setPhones(setPhoneCreateDtoFirst);
        userCreateDtoFirst.setBankAccount(bankAccountCreateDtoFirst);

        userCreateDtoSecond = UserCreateDto.builder()
                .login("SecondLogin")
                .password("SecondPassword")
                .firstName("SecondSecondName")
                .lastName("SecondLastName")
                .middleName("SecondMiddleName")
                .dateOfBirth(secondDate)
                .build();

        bankAccountCreateDtoSecond = BankAccountCreateDto.builder().balance(balanceSecond).build();

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

        userCreateDtoSecond.setEmails(setEmailCreateDtoSecond);
        userCreateDtoSecond.setPhones(setPhoneCreateDtoSecond);
        userCreateDtoSecond.setBankAccount(bankAccountCreateDtoSecond);

        userCreateDtoThird = UserCreateDto.builder()
                .login("ThirdLogin")
                .password("ThirdPassword")
                .firstName("ThirdThirdName")
                .lastName("ThirdLastName")
                .middleName("ThirdMiddleName")
                .dateOfBirth(thirdDate)
                .build();

        bankAccountCreateDtoThird = BankAccountCreateDto.builder().balance(balanceThird).build();

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

        userCreateDtoThird.setEmails(setEmailCreateDtoThird);
        userCreateDtoThird.setPhones(setPhoneCreateDtoThird);
        userCreateDtoThird.setBankAccount(bankAccountCreateDtoThird);
    }

    protected void initUserShortResponseDto() {
        userShortResponseDtoFirst = UserShortResponseDto.builder()
                .id(firstId)
                .login("FirstLogin")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .build();

        emailShortResponseDtoFirst = List.of(EmailShortResponseDto.builder().email("First@email.ru").build());

        phoneShortResponseDtoFirst = List.of(PhoneShortResponseDto.builder().phone("+79000000001").build());

        bankAccountShortResponseDto = BankAccountShortResponseDto.builder()
                .balance(BigDecimal.valueOf(50000.00))
                .build();

        userShortResponseDtoFirst.setBankAccount(bankAccountShortResponseDto);
        userShortResponseDtoFirst.setEmails(emailShortResponseDtoFirst);
        userShortResponseDtoFirst.setPhones(phoneShortResponseDtoFirst);
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

        emailShortResponseDtoFirst = List.of(EmailShortResponseDto.builder().email("First@email.ru").build());

        phoneShortResponseDtoFirst = List.of(PhoneShortResponseDto.builder().phone("+79000000001").build());

        bankAccountShortResponseDto = BankAccountShortResponseDto.builder()
                .balance(balanceFirst)
                .build();

        userFullResponseDtoFirst.setBankAccount(bankAccountShortResponseDto);
        userFullResponseDtoFirst.setEmails(emailShortResponseDtoFirst);
        userFullResponseDtoFirst.setPhones(phoneShortResponseDtoFirst);


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

        emailShortResponseDtoSecond = List.of(
                EmailShortResponseDto.builder()
                        .email("Second1@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Second2@email.ru")
                        .build());

        phoneShortResponseDtoSecond = List.of(
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

        emailShortResponseDtoThird = List.of(
                EmailShortResponseDto.builder()
                        .email("Third1@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Third2@email.ru")
                        .build(),
                EmailShortResponseDto.builder()
                        .email("Third3@email.ru")
                        .build());

        phoneShortResponseDtoThird = List.of(
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