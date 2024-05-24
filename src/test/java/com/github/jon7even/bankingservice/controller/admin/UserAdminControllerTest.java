/*
package com.github.jon7even.bankingservice.controller.admin;

import com.github.jon7even.bankingservice.setup.SetupControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.format.DateTimeFormatter;

import static com.github.jon7even.bankingservice.constants.ControllerApi.PATH_ADMIN;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_USERS;
import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAdminControllerTest extends SetupControllerTest {
    @BeforeEach public void setupMapperTest() {
        initUserCreateDto();
        initUserFullResponseDto();
    }

    @DisplayName("[create] Новый пользователь должен создаться с релевантными полями и присвоить ID")
    @Test public void shouldCreateNewUser_thenReturn_Status201AndUserFullResponseDto() throws Exception {
        mockMvc.perform(post(PATH_ADMIN + PATH_USERS)
                        .content(objectMapper.writeValueAsString(userCreateDtoFirst))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(firstId))
                .andExpect(jsonPath("login").value(userFullResponseDtoFirst.getLogin()))
                .andExpect(jsonPath("email").value(userFullResponseDtoFirst.getEmail()))
                .andExpect(jsonPath("phone").value(userFullResponseDtoFirst.getPhone()))
                .andExpect(jsonPath("confirmedPhone").value(userFullResponseDtoFirst.isConfirmedPhone()))
                .andExpect(jsonPath("firstName").value(userFullResponseDtoFirst.getFirstName()))
                .andExpect(jsonPath("lastName").value(userFullResponseDtoFirst.getLastName()))
                .andExpect(jsonPath("middleName").value(userFullResponseDtoFirst.getMiddleName()))
                .andExpect(jsonPath("dateOfBirth").value(
                        userFullResponseDtoFirst.getDateOfBirth().format(DateTimeFormatter.ofPattern(DATE_DEFAULT)))
                )
                .andExpect(jsonPath("registeredOn").exists())
                .andExpect(jsonPath("updatedOn").value(userFullResponseDtoFirst.getUpdatedOn()));
    }
}*/
