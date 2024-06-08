package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailUpdateDto;
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
public class ApiUserEmailControllerTest extends SetupControllerTest {

    @BeforeEach public void setupApiUserSearchTypeControllerTest() {
        initUserEntity();
        initUserFullResponseDto();

        UserEntity userEntityFirst = userRepository.save(userEntityFirstWithoutId);
        listUserEmailEntitiesFirstWithoutId.forEach(u -> u.setOwner(userEntityFirst));
        userEmailRepository.saveAll(listUserEmailEntitiesFirstWithoutId);

        UserEntity userEntitySecond = userRepository.save(userEntitySecondWithoutId);
        listUserEmailEntitiesSecondWithoutId.forEach(u -> u.setOwner(userEntitySecond));
        userEmailRepository.saveAll(listUserEmailEntitiesSecondWithoutId);
    }

    @DisplayName("[create] Новый email должен добавиться в профиль существующего пользователя")
    @Test public void shouldCreateNewEmail_thenReturn_Status201AndEmailShortResponseDto() throws Exception {
        EmailCreateDto emailCreateDto = EmailCreateDto.builder().email("TestCreate@email.ru").build();

        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_EMAIL, userEntityFirst.getId())
                        .content(objectMapper.writeValueAsString(emailCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").value(emailCreateDto.getEmail()));
    }

    @DisplayName("[create] Новый email не должен добавиться: такой email уже существует")
    @Test public void shouldNotCreateNewEmail_thenReturn_Status409_AlreadyExist() throws Exception {
        EmailCreateDto emailCreateDto = EmailCreateDto.builder().email("First@email.ru").build();

        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_EMAIL, userEntityFirst.getId())
                        .content(objectMapper.writeValueAsString(emailCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [адрес электронной почты] произошла ошибка, "
                                + "пришло нерелевантное значение: [First@email.ru уже существует]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[update] Существующий email должен обновиться в профиле существующего пользователя")
    @Test public void shouldUpdateEmail_thenReturn_Status204AndEmailShortResponseDto() throws Exception {
        EmailUpdateDto emailUpdateDto = EmailUpdateDto.builder().id(firstId).email("TestCreate@email.ru").build();

        mockMvc.perform(MockMvcRequestBuilders.patch(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID, userEntityFirst.getId(), firstId)
                        .content(objectMapper.writeValueAsString(emailUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(emailUpdateDto.getEmail()));

        assertThat(userEmailRepository.findById(userEntityFirst.getId()).get().getEmail())
                .isNotNull()
                .isEqualTo(emailUpdateDto.getEmail());
    }

    @DisplayName("[update] Существующий email не должен обновиться: такой email уже существует")
    @Test public void shouldNotUpdateEmail_thenReturn_Status409_AlreadyExist() throws Exception {
        EmailUpdateDto emailUpdateDto = EmailUpdateDto.builder().id(firstId).email("First@email.ru").build();

        mockMvc.perform(MockMvcRequestBuilders.patch(ControllerApi.PATH_API + PATH_USERS
                                + PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID, userEntityFirst.getId(), firstId)
                        .content(objectMapper.writeValueAsString(emailUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [адрес электронной почты] произошла ошибка, "
                                + "пришло нерелевантное значение: [First@email.ru уже существует]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[delete] Существующий email должен удалиться из профиля пользователя")
    @Test public void shouldDeleteEmail_thenReturn_Status204() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ControllerApi.PATH_API + PATH_USERS
                        + PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID, userEntitySecond.getId(), thirdId))
                .andExpect(status().isNoContent());

        assertThat(userEmailRepository.findById(thirdId))
                .isEmpty();
    }

    @DisplayName("[delete] Существующий email не должен удалиться: последний email нельзя удалить")
    @Test public void shouldNotDeleteEmail_thenReturn_Status409_SizeOfListMin2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ControllerApi.PATH_API + PATH_USERS
                        + PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID, userEntityFirst.getId(), firstId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("reason").value("Integrity constraint has been violated."))
                .andExpect(jsonPath("message")
                        .value("При проверке поля [адрес электронной почты] произошла ошибка, "
                                + "пришло нерелевантное значение: "
                                + "[чтобы выполнить эту операцию минимальный размер должен быть больше 1]"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));

        assertThat(userEmailRepository.findById(firstId))
                .isNotEmpty();
    }
}
