package com.github.jon7even.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jon7even.BankingServiceApp;
import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.security.SignInRequestDto;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.setup.SetupContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = BankingServiceApp.class)
@AutoConfigureMockMvc(addFilters = true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(properties = {"SERVER_EXTERNAL_PORT_APPLICATION_TEST=8097", "SCHEMA_APP=application",
        "SECURITY_TOKEN_KEY_TEST=2kjhgO987TG969876RF98ftGR8765DE465877908y90GOIUF8676RF86",
        "SECURITY_TOKEN_LIFETIME_TEST=1800000"})
public class AuthorizationControllerTest extends SetupContainerTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected UserRepository userRepository;
    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected JdbcTemplate jdbcTemplate;
    private SignInRequestDto signInRequestDtoFirst;


    @BeforeEach public void setupSystemUserControllerTest() {
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE application.user_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.bank_account_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.email_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.phone_seq RESTART WITH 1");
        initUserEntity();
        userEntityFirstWithoutId.setPassword(passwordEncoder.encode(userEntityFirstWithoutId.getPassword()));
        userRepository.save(userEntityFirstWithoutId);
        signInRequestDtoFirst = SignInRequestDto.builder()
                .login(userEntityFirstWithoutId.getLogin())
                .password(userEntityFirst.getPassword())
                .build();
    }

    @DisplayName("[signIn] Пользователь должен авторизоваться")
    @Test public void shouldSignIn_thenReturn_Status200AndJwtAuthResponseDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_AUTH)
                        .content(objectMapper.writeValueAsString(signInRequestDtoFirst))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").exists())
                .andExpect(jsonPath("token").isNotEmpty());
    }

    @DisplayName("[signIn] Пользователь не должен авторизоваться [введен неправильный пароль]")
    @Test public void shouldNotSignIn_thenReturn_Status403AndBadPassword() throws Exception {
        signInRequestDtoFirst.setPassword("123");
        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_AUTH)
                        .content(objectMapper.writeValueAsString(signInRequestDtoFirst))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("status").value("FORBIDDEN"))
                .andExpect(jsonPath("reason").value("Access denied."))
                .andExpect(jsonPath("message")
                        .value("На этой странице доступ запрещен, авторизуйтесь по адресу /auth"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
    }

    @DisplayName("[signIn] Пользователь не должен авторизоваться [пользователь не найден]")
    @Test public void shouldNotSignIn_thenReturn_Status404AndUserNotFound() throws Exception {
        signInRequestDtoFirst.setLogin("123");
        mockMvc.perform(MockMvcRequestBuilders.post(ControllerApi.PATH_AUTH)
                        .content(objectMapper.writeValueAsString(signInRequestDtoFirst))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("reason").value("The required object was not found."))
                .andExpect(jsonPath("message").value("Пользователь с [login=123] не был найден"))
                .andExpect(jsonPath("timestamp").value(notNullValue()));
        ;
    }
}

