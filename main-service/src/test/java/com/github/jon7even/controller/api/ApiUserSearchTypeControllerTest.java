package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.constants.ControllerUser;
import com.github.jon7even.constants.DateTimeFormat;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.setup.SetupControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ApiUserSearchTypeControllerTest extends SetupControllerTest {

    @BeforeEach public void setupApiUserSearchTypeControllerTest() {
        initUserEntity();
        initUserFullResponseDto();

        UserEntity userEntityFirst = userRepository.save(userEntityFirstWithoutId);
        listUserEmailEntitiesFirstWithoutId.forEach(u -> u.setOwner(userEntityFirst));
        userEmailRepository.saveAll(listUserEmailEntitiesFirstWithoutId);
        listUserPhoneEntitiesFirstWithoutId.forEach(u -> u.setOwner(userEntityFirst));
        userPhoneRepository.saveAll(listUserPhoneEntitiesFirstWithoutId);

        UserEntity userEntitySecond = userRepository.save(userEntitySecondWithoutId);
        listUserEmailEntitiesSecondWithoutId.forEach(u -> u.setOwner(userEntitySecond));
        userEmailRepository.saveAll(listUserEmailEntitiesSecondWithoutId);
        listUserPhoneEntitiesSecondWithoutId.forEach(u -> u.setOwner(userEntitySecond));
        userPhoneRepository.saveAll(listUserPhoneEntitiesSecondWithoutId);

        UserEntity userEntityThird = userRepository.save(userEntityThirdWithoutId);
        listUserEmailEntitiesThirdWithoutId.forEach(u -> u.setOwner(userEntityThird));
        userEmailRepository.saveAll(listUserEmailEntitiesThirdWithoutId);
        listUserPhoneEntitiesThirdWithoutId.forEach(u -> u.setOwner(userEntityThird));
        userPhoneRepository.saveAll(listUserPhoneEntitiesThirdWithoutId);
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по телефону с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamPhoneSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("phone", "+70000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по телефону с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamPhoneSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("phone", "+70000000000")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по эл. почте с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamEmailSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("email", "123@ya.ru")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по эл. почте с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamEmailSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("email", "123@ya.ru")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по дате рождения с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamDateOfBirthSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("dateOfBirth", "01-01-3000")
                        .param("sort", "USER_ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по дате рождения с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamDateOfBirthSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("dateOfBirth", "01-01-3000")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по имени с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamFirstNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по имени с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamFirstNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по фамилии с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamLastNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("lastName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по фамилии с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamLastNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("lastName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по отчеству с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamMiddleNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("middleName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по отчеству с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamMiddleNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("middleName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по ФИО с сортировкой по возрастанию")
    @Test public void shouldNotFindUsersByParamFullNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "none")
                        .param("lastName", "none")
                        .param("middleName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователей по ФИО с сортировкой по убыванию")
    @Test public void shouldNotFindUsersByParamFullNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "none")
                        .param("lastName", "none")
                        .param("middleName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Должен найти всех пользователей [без параметров] с сортировкой ID по возрастанию")
    @Test public void shouldFindAllUsersByNoParamsSortASC_thenReturn_Status200AndListOfThirdUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("sort", "USER_ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(firstId))
                .andExpect(jsonPath("$.[1].id").value(secondId))
                .andExpect(jsonPath("$.[2].id").value(thirdId))
                .andExpect(jsonPath("$", hasSize(userRepository.findAll().size())));
    }

    @DisplayName("[searchUserByParam] Должен найти всех пользователей [без параметров] с сортировкой ID по убыванию")
    @Test public void shouldFindAllUsersByNoParamsSortDESC_thenReturn_Status200AndListOfThirdUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(thirdId))
                .andExpect(jsonPath("$.[1].id").value(secondId))
                .andExpect(jsonPath("$.[2].id").value(firstId))
                .andExpect(jsonPath("$", hasSize(userRepository.findAll().size())));
    }

    @DisplayName("[searchUserByParam] Должен найти пользователя по [номеру телефона]")
    @Test public void shouldFindUserByPhone_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("phone", listUserPhoneEntitiesThirdWithoutId.stream().findFirst().get().getPhone())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(thirdId))
                .andExpect(jsonPath("$.[0].emails.[0].email").value(listUserEmailEntitiesThird.stream()
                        .findFirst().get().getEmail()))
                .andExpect(jsonPath("$.[0].phones.[0].phone").value(listUserPhoneEntitiesThird.stream()
                        .findFirst().get().getPhone()));
    }

    @DisplayName("[searchUserByParam] Должен найти пользователя по [электронной почте]")
    @Test public void shouldFindUserByEmail_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("email", listUserEmailEntitiesSecond.stream().findFirst().get().getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(secondId))
                .andExpect(jsonPath("$.[0].emails.[0].email").value(listUserEmailEntitiesSecond.stream()
                        .findFirst().get().getEmail()))
                .andExpect(jsonPath("$.[0].phones.[0].phone").value(listUserPhoneEntitiesSecond.stream()
                        .findFirst().get().getPhone()));
    }

    @DisplayName("[searchUserByParam] Должен найти всех пользователей по [дате рождения] по возрастанию даты рождения")
    @Test public void shouldFindUsersByDateOfBirthSortASC_thenReturn_Status200AndListOfThirdUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("dateOfBirth", LocalDate.of(1980, 2, 16)
                                .format(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_DEFAULT)))
                        .param("sort", "USER_ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(thirdId))
                .andExpect(jsonPath("$.[1].id").value(secondId))
                .andExpect(jsonPath("$.[2].id").value(firstId))
                .andExpect(jsonPath("$", hasSize(userRepository.findAll().size())));
    }

    @DisplayName("[searchUserByParam] Должен найти двух пользователей по [дате рождения] по убыванию даты рождения")
    @Test public void shouldFindUsersByDateOfBirthSortDESC_thenReturn_Status200AndListOfSecondUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("dateOfBirth", LocalDate.of(1981, 2, 17)
                                .format(DateTimeFormatter.ofPattern(DateTimeFormat.DATE_DEFAULT)))
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(firstId))
                .andExpect(jsonPath("$.[1].id").value(secondId))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("[searchUserByParam] Должен найти одного пользователя по имени")
    @Test public void shouldFindUsersByParamFirstName_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "FirstfirstName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(firstId))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("[searchUserByParam] Должен найти одного пользователя по фамилии")
    @Test public void shouldFindUsersByParamLastName_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("lastName", "Sec")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(secondId))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("[searchUserByParam] Должен найти одного пользователя по отчеству")
    @Test public void shouldFindUsersByParamMiddleName_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("middleName", "se")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(secondId))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @DisplayName("[searchUserByParam] Должен найти одного пользователя по ФИО")
    @Test public void shouldFindUsersByParamFullName_thenReturn_Status200AndListOfOneUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ControllerApi.PATH_API + ControllerUser.PATH_USERS + ControllerUser.PATH_SEARCH)
                        .param("firstName", "Thir")
                        .param("lastName", "thi")
                        .param("middleName", "th")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(thirdId))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}