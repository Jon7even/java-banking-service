package com.github.jon7even.bankingservice.setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jon7even.bankingservice.repository.UserEmailRepository;
import com.github.jon7even.bankingservice.repository.UserPhoneRepository;
import com.github.jon7even.bankingservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@SpringBootTest
@AutoConfigureMockMvc
public class SetupControllerTest extends SetupContainerTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected UserRepository userRepository;
    @Autowired protected UserEmailRepository userEmailRepository;
    @Autowired protected UserPhoneRepository userPhoneRepository;
    @Autowired protected JdbcTemplate jdbcTemplate;

    @BeforeEach protected void clearRepositories() {
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE application.user_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.email_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.phone_seq RESTART WITH 1");
    }
}
