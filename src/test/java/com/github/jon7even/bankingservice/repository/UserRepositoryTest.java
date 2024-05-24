package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.setup.SetupRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends SetupRepositoryTest {
    @BeforeEach public void setupMapperTest() {
        initUserEntity();
    }

    @DisplayName("[save] Должен сохранить нового пользователя и присвоить ID")
    @Test public void shouldCreateNewUser_thenReturn_UserEntity() {
        UserEntity actualUserInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);

        assertThat(actualUserInRepository.getId())
                .isNotNull()
                .isEqualTo(firstId);

        assertThat(actualUserInRepository.getLogin())
                .isNotNull()
                .isEqualTo(userEntityFirst.getLogin());

        assertThat(actualUserInRepository.getPassword())
                .isNotNull()
                .isEqualTo(userEntityFirst.getPassword());

        assertThat(actualUserInRepository.getEmails())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(1)
                .isEqualTo(userEntityFirst.getEmails());

        assertThat(actualUserInRepository.getPhones())
                .isNotEmpty()
                .doesNotContainNull()
                .hasSize(1)
                .isEqualTo(userEntityFirst.getPhones());

        assertThat(actualUserInRepository.getFirstName())
                .isNotNull()
                .isEqualTo(userEntityFirst.getFirstName());

        assertThat(actualUserInRepository.getLastName())
                .isNotNull()
                .isEqualTo(userEntityFirst.getLastName());

        assertThat(actualUserInRepository.getMiddleName())
                .isNotNull()
                .isEqualTo(userEntityFirst.getMiddleName());

        assertThat(actualUserInRepository.getDateOfBirth())
                .isNotNull()
                .isEqualTo(userEntityFirst.getDateOfBirth());

        assertThat(actualUserInRepository.getRegisteredOn())
                .isNotNull()
                .isEqualTo(userEntityFirst.getRegisteredOn());

        assertThat(actualUserInRepository.getUpdatedOn())
                .isNull();
    }

    @DisplayName("[existsByLogin] Должен вернуть True когда пользователь с таким [login] есть в БД")
    @Test public void shouldReturnTrue_whenLoginExistInRepository() {
        UserEntity actualUserInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        assertThat(actualUserInRepository.getId())
                .isNotNull()
                .isEqualTo(firstId);
/*        assertThat(userRepository.existsByLogin(userEntityFirst.getLogin()))
                .isTrue();*/
    }
/*
    @DisplayName("[existsByLogin] Должен вернуть False когда пользователя с таким [login] нет в БД")
    @Test public void shouldReturnFalse_whenThisLoginIsNotExistInRepository() {
        userRepository.saveAndFlush(userEntitySecond);
        assertThat(userRepository.existsByLogin(userEntityFirst.getLogin()))
                .isFalse();
    }

    @DisplayName("[existsByEmail] Должен вернуть True когда пользователь с таким [email] есть в БД")
    @Test public void shouldReturnTrue_whenEmailExistInRepository() {
        userRepository.saveAndFlush(userEntityFirst);
        assertThat(userEmailRepository.existsByEmail(userEntityFirst.getEmails().toString()))
                .isTrue();
    }

    @DisplayName("[existsByEmail] Должен вернуть False когда пользователя с таким [email] нет в БД")
    @Test public void shouldReturnFalse_whenThisEmailIsNotExistInRepository() {
        userRepository.saveAndFlush(userEntitySecond);
        assertThat(userEmailRepository.existsByEmail(userEntityFirst.getEmails().toString()))
                .isFalse();
    }



    @DisplayName("[existsByPhone] Должен вернуть True когда пользователь с таким [phone] есть в БД")
    @Test public void shouldReturnTrue_whenPhoneExistInRepository() {
        userRepository.save(userEntityFirst);
        assertThat(userRepository.existsByEmail(userEntityFirst.getEmail()))
                .isTrue();
    }

    @DisplayName("[existsByPhone] Должен вернуть False когда пользователя с таким [phone] нет в БД")
    @Test public void shouldReturnFalse_whenThisPhoneIsNotExistInRepository() {
        userRepository.save(userEntitySecond);
        assertThat(userRepository.existsByEmail(userEntityFirst.getLogin()))
                .isFalse();
    }*/
}
