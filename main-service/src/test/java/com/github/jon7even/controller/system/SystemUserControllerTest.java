package com.github.jon7even.controller.system;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.constants.ControllerUser;
import com.github.jon7even.setup.SetupControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeFormatter;

import static com.github.jon7even.constants.DateTimeFormat.DATE_DEFAULT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class SystemUserControllerTest extends SetupControllerTest {

    @BeforeEach public void setupSystemUserControllerTest() {
        initUserCreateDto();
        initUserFullResponseDto();
    }

    @DisplayName("[create] Новый пользователь должен создаться с релевантными полями и присвоить ID")
    @Test public void shouldCreateNewUser_thenReturn_Status201AndUserFullResponseDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_SYSTEM + ControllerUser.PATH_USERS)
                        .content(objectMapper.writeValueAsString(userCreateDtoFirst))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(firstId))
                .andExpect(jsonPath("login").value(userFullResponseDtoFirst.getLogin()))
                .andExpect(jsonPath("bankAccount.balance")
                        .value(userFullResponseDtoFirst.getBankAccount().getBalance()))
                .andExpect(jsonPath("$.emails.[0].email").value(userFullResponseDtoFirst.getEmails().stream()
                        .findFirst().get().getEmail()))
                .andExpect(jsonPath("$.phones.[0].phone").value(userFullResponseDtoFirst.getPhones().stream()
                        .findFirst().get().getPhone()))
                .andExpect(jsonPath("firstName").value(userFullResponseDtoFirst.getFirstName()))
                .andExpect(jsonPath("lastName").value(userFullResponseDtoFirst.getLastName()))
                .andExpect(jsonPath("middleName").value(userFullResponseDtoFirst.getMiddleName()))
                .andExpect(jsonPath("dateOfBirth")
                        .value(userFullResponseDtoFirst.getDateOfBirth()
                                .format(DateTimeFormatter.ofPattern(DATE_DEFAULT))))
                .andExpect(jsonPath("registeredOn").exists())
                .andExpect(jsonPath("updatedOn").value(userFullResponseDtoFirst.getUpdatedOn()));
    }
}
