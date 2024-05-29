package com.github.jon7even.repository;

import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.setup.SetupRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserRepositoryTest extends SetupRepositoryTest {

    @DisplayName("[save] Должен сохранить нового пользователя и присвоить ID")
    @Test public void shouldCreateNewUser_thenReturn_UserEntityWithId() {
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

        assertThat(actualUserInRepository.getBankAccountEntity())
                .isNull();

        assertThat(actualUserInRepository.getEmails())
                .isEmpty();

        assertThat(actualUserInRepository.getPhones())
                .isEmpty();

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

    @DisplayName("[findById] Должен найти пользователя по его ID")
    @Test public void shouldFindUserWithoutOneToMany_thenReturn_UserEntity() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        bankAccountRepository.saveAndFlush(bankAccountEntityFirstWithoutId);
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);


        UserEntity actualUserInRepository = userRepository.findById(userInRepository.getId()).get();

        assertThat(actualUserInRepository.getId())
                .isNotNull()
                .isEqualTo(firstId);

        assertThat(actualUserInRepository.getLogin())
                .isNotNull()
                .isEqualTo(userEntityFirst.getLogin());

        assertThat(actualUserInRepository.getPassword())
                .isNotNull()
                .isEqualTo(userEntityFirst.getPassword());

        assertThat(actualUserInRepository.getBankAccountEntity())
                .isNull();

        assertThat(actualUserInRepository.getEmails())
                .isEmpty();

        assertThat(actualUserInRepository.getPhones())
                .isEmpty();

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
        userRepository.saveAndFlush(userEntityFirstWithoutId);

        assertThat(userRepository.existsByLogin(userEntityFirst.getLogin()))
                .isTrue();
    }

    @DisplayName("[existsByLogin] Должен вернуть False когда пользователя с таким [login] нет в БД")
    @Test public void shouldReturnFalse_whenLoginIsNotExistInRepository() {
        userRepository.saveAndFlush(userEntitySecondWithoutId);

        assertThat(userRepository.existsByLogin(userEntityFirst.getLogin()))
                .isFalse();
    }
}
