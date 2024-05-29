package com.github.jon7even.bankingservice.entity;

import com.github.jon7even.bankingservice.setup.PreparationObjectsForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityTest extends PreparationObjectsForTests {

    @BeforeEach public void setupMapperTest() {
        initUserEntity();
    }

    @DisplayName("Метод сравнения должен выдать положительный ответ для одинаковых объектов")
    @Test public void toEquals_ActualUserWithExpectedUser_ReturnTrue() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [логина] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfLogin_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("Change")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [пароля] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfPassword_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("Change")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [имени] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfFirstName_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("Change")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [фамилии] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfSecondName_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("Change")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [отчества] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfMiddleName_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("Change")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [даты рождения] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfDateOfBirth_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(secondDate)
                .registeredOn(firstDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [времени регистрации] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfRegisteredOn_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(secondDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [времени обновления] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfUpdatedOn_ReturnFalse() {
        UserEntity thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .updatedOn(secondDateTime)
                .build();

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }
}
