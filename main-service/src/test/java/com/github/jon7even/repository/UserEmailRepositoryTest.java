package com.github.jon7even.repository;

import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.setup.SetupRepositoryTest;
import com.github.jon7even.entity.UserEmailEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserEmailRepositoryTest extends SetupRepositoryTest {

    @DisplayName("[save] Должен сохранить электронный адрес пользователя и присвоить ID")
    @Test public void shouldAddNewEmail_thenReturn_UserEmailEntityWithId() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        UserEmailEntity actualUserEmailEntity = userEmailRepository.save(listUserEmailEntitiesFirstWithoutId.stream()
                .findFirst().get());

        assertThat(actualUserEmailEntity)
                .isNotNull()
                .isEqualTo(listUserEmailEntitiesFirst.stream().findFirst().get());
    }

    @DisplayName("[saveAll] Должен сохранить список электронных адресов пользователя и присвоить ID")
    @Test public void shouldAddNewListOfEmails_thenReturn_ListOfUserEmailEntityWithId() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityThirdWithoutId);
        listUserEmailEntitiesThirdWithoutId.forEach(u -> u.setOwner(userInRepository));
        List<UserEmailEntity> actualUserEmailEntity = userEmailRepository.saveAllAndFlush(
                listUserEmailEntitiesThirdWithoutId
        );

        assertThat(actualUserEmailEntity)
                .isNotEmpty()
                .hasSize(listUserEmailEntitiesThird.size())
                .isEqualTo(listUserEmailEntitiesThird);
    }

    @DisplayName("[existsByEmail] Должен вернуть True когда пользователь с таким [email] есть в БД")
    @Test public void shouldReturnTrue_whenEmailExistInRepository() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);

        assertThat(userEmailRepository.existsByEmail(listUserEmailEntitiesFirstWithoutId.stream()
                .findFirst().get().getEmail()))
                .isTrue();
    }

    @DisplayName("[existsByEmail] Должен вернуть False когда пользователя с таким [email] нет в БД")
    @Test public void shouldReturnFalse_whenEmailIsNotExistInRepository() {
        userRepository.saveAndFlush(userEntitySecondWithoutId);

        assertThat(userEmailRepository.existsByEmail(listUserEmailEntitiesFirstWithoutId.stream()
                .findFirst().get().getEmail()))
                .isFalse();
    }

    @DisplayName("[getEmailEntityBySetEmails] Должен найти список из одного [email], который есть в БД")
    @Test public void shouldFindListOfEmailBySetEmails_thenReturn_ListOfOneSize() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);
        Set<String> actualSearchSetOfEmail = listUserEmailEntitiesFirst.stream()
                .map(UserEmailEntity::getEmail).collect(Collectors.toSet());

        assertThat(userEmailRepository.getEmailEntityBySetEmails(actualSearchSetOfEmail))
                .isNotEmpty()
                .hasSize(actualSearchSetOfEmail.size());
    }

    @DisplayName("[getEmailEntityBySetEmails] Должен найти список из тех [email], которые есть в БД")
    @Test public void shouldFindListOfEmailBySetEmails_thenReturn_ListOfSomeSize() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityThirdWithoutId);
        listUserEmailEntitiesThirdWithoutId.forEach(u -> u.setOwner(userInRepository));
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesThirdWithoutId);
        Set<String> actualSearchSetOfEmail = listUserEmailEntitiesThird.stream()
                .map(UserEmailEntity::getEmail).collect(Collectors.toSet());
        actualSearchSetOfEmail.add(listUserEmailEntitiesFirst.stream().findFirst().get().getEmail());

        assertThat(userEmailRepository.getEmailEntityBySetEmails(actualSearchSetOfEmail))
                .isNotEmpty()
                .hasSize(actualSearchSetOfEmail.size() - 1);
    }

    @DisplayName("[getEmailEntityBySetEmails] Должен вернуть пустой список [email], если искать email, которого нет")
    @Test public void shouldNotFindListOfEmailBySetEmails_thenReturn_EmptyList() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);
        Set<String> actualSearchSetOfEmail = listUserEmailEntitiesSecond.stream()
                .map(UserEmailEntity::getEmail).collect(Collectors.toSet());

        assertThat(userEmailRepository.getEmailEntityBySetEmails(actualSearchSetOfEmail))
                .isEmpty();
    }

    @DisplayName("[findByOwnerId] должен найти список [email] по id владельца")
    @Test public void shouldFindListOfEmailByOwner_thenReturn_ListOfOneSize() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        List<UserEmailEntity> actualUserEmailEntity =
                userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);

        assertThat(userEmailRepository.findByOwnerId(userInRepository.getId()))
                .isNotEmpty()
                .isEqualTo(actualUserEmailEntity);
    }

    @DisplayName("[findByOwnerId] не должен найти список [email] по id владельца")
    @Test public void shouldNotFindListOfEmailByOwner_thenReturn_EmptyList() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        userEmailRepository.saveAllAndFlush(listUserEmailEntitiesFirstWithoutId);

        assertThat(userEmailRepository.findByOwnerId(userInRepository.getId() + 1L))
                .isEmpty();
    }
}
