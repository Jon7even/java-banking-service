package com.github.jon7even.bankingservice.controller.api;

import com.github.jon7even.bankingservice.setup.SetupControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.jon7even.bankingservice.constants.ControllerApi.PATH_API;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_SEARCH;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_USERS;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiUserSearchControllerTest extends SetupControllerTest {
    @BeforeEach
    public void setupControllerTest() {
        initUserEntity();
        initUserFullResponseDto();
        userRepository.save(userEntityFirstWithoutId);
        userRepository.save(userEntitySecondWithoutId);
        userRepository.save(userEntityThirdWithoutId);
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по телефону с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamPhoneSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("phone", "+70000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по телефону с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamPhoneSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("phone", "+70000000000")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по эл. почте с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamEmailSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("email", "123@ya.ru")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по эл. почте с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamEmailSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("email", "123@ya.ru")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по дате рождения с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamDateOfBirthSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("dateOfBirth", "01-01-3000")
                        .param("sort", "USER_ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по дате рождения с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamDateOfBirthSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("dateOfBirth", "01-01-3000")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по имени с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamFirstNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("firstName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по имени с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamFirstNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("firstName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по фамилии с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamLastNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("lastName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по фамилии с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamLastNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("lastName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по отчеству с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamMiddleNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("middleName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по отчеству с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamMiddleNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("middleName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по ФИО с сортировкой по возрастанию")
    @Test public void shouldNotFindUserByParamFullNameSortASC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("firstName", "none")
                        .param("lastName", "none")
                        .param("middleName", "none")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @DisplayName("[searchUserByParam] Не должен найти пользователя по ФИО с сортировкой по убыванию")
    @Test public void shouldNotFindUserByParamFullNameSortDESC_thenReturn_Status200AndEmptyList() throws Exception {
        mockMvc.perform(get(PATH_API + PATH_USERS + PATH_SEARCH)
                        .param("firstName", "none")
                        .param("lastName", "none")
                        .param("middleName", "none")
                        .param("sort", "USER_DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}