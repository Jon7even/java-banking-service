package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.entity.UserPhoneEntity;
import com.github.jon7even.bankingservice.setup.SetupRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserPhoneRepositoryTest extends SetupRepositoryTest {

    @DisplayName("[save] Должен сохранить телефонный номер пользователя и присвоить ID")
    @Test public void shouldAddNewPhone_thenReturn_UserPhoneEntityWithId() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        UserPhoneEntity actualUserPhoneEntity = userPhoneRepository.save(listUserPhoneEntitiesFirstWithoutId.stream()
                .findFirst().get());

        assertThat(actualUserPhoneEntity)
                .isNotNull()
                .isEqualTo(listUserPhoneEntitiesFirst.stream().findFirst().get());
    }

    @DisplayName("[saveAll] Должен сохранить список номеров телефона пользователя и присвоить ID")
    @Test public void shouldAddNewListOfPhones_thenReturn_ListOfUserPhoneEntityWithId() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityThirdWithoutId);
        listUserPhoneEntitiesThirdWithoutId.forEach(u -> u.setOwner(userInRepository));
        List<UserPhoneEntity> actualUserPhoneEntity = userPhoneRepository.saveAllAndFlush(
                listUserPhoneEntitiesThirdWithoutId
        );

        assertThat(actualUserPhoneEntity)
                .isNotEmpty()
                .hasSize(listUserPhoneEntitiesThird.size())
                .isEqualTo(listUserPhoneEntitiesThird);
    }

    @DisplayName("[existsByPhone] Должен вернуть True когда пользователь с таким [phone] есть в БД")
    @Test public void shouldReturnTrue_whenPhoneExistInRepository() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);

        assertThat(userPhoneRepository.existsByPhone(listUserPhoneEntitiesFirstWithoutId.stream()
                .findFirst().get().getPhone()))
                .isTrue();
    }

    @DisplayName("[existsByPhone] Должен вернуть False когда пользователя с таким [phone] нет в БД")
    @Test public void shouldReturnFalse_whenPhoneIsNotExistInRepository() {
        userRepository.saveAndFlush(userEntitySecondWithoutId);

        assertThat(userPhoneRepository.existsByPhone(listUserPhoneEntitiesFirstWithoutId.stream()
                .findFirst().get().getPhone()))
                .isFalse();
    }

    @DisplayName("[getPhoneEntityBySetPhones] Должен найти список из одного [phone], который есть в БД")
    @Test public void shouldFindListOfPhoneBySetPhones_thenReturn_ListOfOneSize() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);
        Set<String> actualSearchSetOfPhone = listUserPhoneEntitiesFirst.stream()
                .map(UserPhoneEntity::getPhone).collect(Collectors.toSet());

        assertThat(userPhoneRepository.getPhoneEntityBySetPhones(actualSearchSetOfPhone))
                .isNotEmpty()
                .hasSize(actualSearchSetOfPhone.size());
    }

    @DisplayName("[getPhoneEntityBySetPhones] Должен найти список из тех [phone], которые есть в БД")
    @Test public void shouldFindListOfPhoneBySetPhones_thenReturn_ListOfSomeSize() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityThirdWithoutId);
        listUserPhoneEntitiesThirdWithoutId.forEach(u -> u.setOwner(userInRepository));
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesThirdWithoutId);
        Set<String> actualSearchSetOfPhone = listUserPhoneEntitiesThird.stream()
                .map(UserPhoneEntity::getPhone).collect(Collectors.toSet());
        actualSearchSetOfPhone.add(listUserPhoneEntitiesFirst.stream().findFirst().get().getPhone());

        assertThat(userPhoneRepository.getPhoneEntityBySetPhones(actualSearchSetOfPhone))
                .isNotEmpty()
                .hasSize(actualSearchSetOfPhone.size() - 1);
    }

    @DisplayName("[getPhoneEntityBySetPhones] Должен вернуть пустой список [phone], если искать phone, которого нет")
    @Test public void shouldNotFindListOfPhoneBySetPhones_thenReturn_EmptyList() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);
        Set<String> actualSearchSetOfPhone = listUserPhoneEntitiesSecond.stream()
                .map(UserPhoneEntity::getPhone).collect(Collectors.toSet());

        assertThat(userPhoneRepository.getPhoneEntityBySetPhones(actualSearchSetOfPhone))
                .isEmpty();
    }

    @DisplayName("[findByOwnerId] должен найти список [phone] по id владельца")
    @Test public void shouldFindListOfPhoneByOwner_thenReturn_ListOfOneSize() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        List<UserPhoneEntity> actualUserPhoneEntity =
                userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);

        assertThat(userPhoneRepository.findByOwnerId(userInRepository.getId()))
                .isNotEmpty()
                .isEqualTo(actualUserPhoneEntity);
    }

    @DisplayName("[findByOwnerId] не должен найти список [phone] по id владельца")
    @Test public void shouldNotFindListOfPhoneByOwner_thenReturn_EmptyList() {
        UserEntity userInRepository = userRepository.saveAndFlush(userEntityFirstWithoutId);
        userPhoneRepository.saveAllAndFlush(listUserPhoneEntitiesFirstWithoutId);

        assertThat(userPhoneRepository.findByOwnerId(userInRepository.getId() + 1L))
                .isEmpty();
    }
}
