package com.github.jon7even.entity;

import com.github.jon7even.setup.PreparationObjectsForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityTest extends PreparationObjectsForTests {
    private UserEntity thatUser;

    @BeforeEach public void setupMapperTest() {
        initUserEntity();
        thatUser = UserEntity.builder()
                .id(firstId)
                .login("FirstLogin")
                .password("FirstPassword")
                .firstName("FirstFirstName")
                .lastName("FirstLastName")
                .middleName("FirstMiddleName")
                .dateOfBirth(firstDate)
                .registeredOn(firstDateTime)
                .build();
    }

    @DisplayName("Метод сравнения должен выдать положительный ответ для одинаковых объектов")
    @Test public void toEquals_ActualUserWithExpectedUser_ReturnTrue() {
        assertThat(thatUser)
                .isNotNull()
                .isEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [логина] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfLogin_ReturnFalse() {
        thatUser.setLogin("Change");

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [пароля] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfPassword_ReturnFalse() {
        thatUser.setPassword("Change");

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [имени] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfFirstName_ReturnFalse() {
        thatUser.setFirstName("Change");

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [фамилии] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfSecondName_ReturnFalse() {
        thatUser.setLastName("Change");

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [отчества] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfMiddleName_ReturnFalse() {
        thatUser.setMiddleName("Change");

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [даты рождения] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfDateOfBirth_ReturnFalse() {
        thatUser.setDateOfBirth(secondDate);

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [времени регистрации] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfRegisteredOn_ReturnFalse() {
        thatUser.setRegisteredOn(secondDateTime);

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }

    @DisplayName("Проверка сравнения для сущности с изменением [времени обновления] должна дать отрицательный ответ")
    @Test public void toNotEquals_ActualUserWithChangeOfUpdatedOn_ReturnFalse() {
        thatUser.setUpdatedOn(secondDateTime);

        assertThat(thatUser)
                .isNotNull()
                .isNotEqualTo(userEntityFirst);
    }
}
