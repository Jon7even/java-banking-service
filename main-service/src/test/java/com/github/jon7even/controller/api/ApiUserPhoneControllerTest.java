package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneUpdateDto;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.setup.SetupControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.jon7even.constants.ControllerUser.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ApiUserPhoneControllerTest extends SetupControllerTest {

    @BeforeEach public void setupApiUserSearchTypeControllerTest() {
        initUserEntity();
        initUserFullResponseDto();

        UserEntity userEntityFirst = userRepository.save(userEntityFirstWithoutId);
        listUserPhoneEntitiesFirstWithoutId.forEach(u -> u.setOwner(userEntityFirst));
        userPhoneRepository.saveAll(listUserPhoneEntitiesFirstWithoutId);

        UserEntity userEntitySecond = userRepository.save(userEntitySecondWithoutId);
        listUserPhoneEntitiesThirdWithoutId.forEach(u -> u.setOwner(userEntitySecond));
        userPhoneRepository.saveAll(listUserPhoneEntitiesThirdWithoutId);
    }

    @DisplayName("[create] Новый phone должен добавиться в профиль существующего пользователя")
    @Test public void shouldCreateNewPhone_thenReturn_Status201AndPhoneShortResponseDto() throws Exception {
        PhoneCreateDto phoneCreateDto = PhoneCreateDto.builder().phone("+79000000000").build();

        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_PHONE, userEntityFirst.getId())
                        .content(objectMapper.writeValueAsString(phoneCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("phone").value(phoneCreateDto.getPhone()));
    }

    @DisplayName("[create] Новый phone не должен добавиться: такой phone уже существует")
    @Test public void shouldNotCreateNewPhone_thenReturn_Status409_AlreadyExist() throws Exception {
        PhoneCreateDto phoneCreateDto = PhoneCreateDto.builder().phone("+79000000001").build();

        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_PHONE, userEntityFirst.getId())
                        .content(objectMapper.writeValueAsString(phoneCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [номер телефона] произошла ошибка, "
                                + "пришло нерелевантное значение: [+79000000001 уже существует]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[update] Существующий phone должен обновиться в профиле существующего пользователя")
    @Test public void shouldUpdatePhone_thenReturn_Status204AndPhoneShortResponseDto() throws Exception {
        PhoneUpdateDto phoneUpdateDto = PhoneUpdateDto.builder().id(firstId).phone("+79000000000").build();

        mockMvc.perform(MockMvcRequestBuilders.patch(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID, userEntityFirst.getId(), firstId)
                        .content(objectMapper.writeValueAsString(phoneUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("phone").value(phoneUpdateDto.getPhone()));

        assertThat(userPhoneRepository.findById(userEntityFirst.getId()).get().getPhone())
                .isNotNull()
                .isEqualTo(phoneUpdateDto.getPhone());
    }

    @DisplayName("[update] Существующий phone не должен обновиться: такой phone уже существует")
    @Test public void shouldNotUpdatePhone_thenReturn_Status409_AlreadyExist() throws Exception {
        PhoneUpdateDto phoneUpdateDto = PhoneUpdateDto.builder().id(firstId).phone("+79000000001").build();

        mockMvc.perform(MockMvcRequestBuilders.patch(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID, userEntityFirst.getId(), firstId)
                        .content(objectMapper.writeValueAsString(phoneUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [номер телефона] произошла ошибка, "
                                + "пришло нерелевантное значение: [+79000000001 уже существует]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[delete] Существующий phone должен удалиться из профиля пользователя")
    @Test public void shouldDeletePhone_thenReturn_Status204() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ControllerApi.PATH_API + PATH_USERS
                        + PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID, userEntitySecond.getId(), fourthId))
                .andExpect(status().isNoContent());

        assertThat(userPhoneRepository.findById(fourthId))
                .isEmpty();
    }

    @DisplayName("[delete] Phone не должен удалиться: не найден по ID")
    @Test public void shouldNotDeletePhone_thenReturn_Status404_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ControllerApi.PATH_API + PATH_USERS
                        + PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID, userEntitySecond.getId(), seventhId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("reason").value("The required object was not found."))
                .andExpect(jsonPath("message")
                        .value("Phone с [phoneId=7] не был найден"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[delete] Существующий phone не должен удалиться: последний phone нельзя удалить")
    @Test public void shouldNotDeletePhone_thenReturn_Status409_SizeOfListMin2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ControllerApi.PATH_API + PATH_USERS
                        + PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID, userEntityFirst.getId(), firstId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [номер телефона] произошла ошибка, "
                                + "пришло нерелевантное значение: "
                                + "[чтобы выполнить эту операцию минимальный размер должен быть больше 1]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));

        assertThat(userPhoneRepository.findById(firstId))
                .isNotEmpty();
    }
}
