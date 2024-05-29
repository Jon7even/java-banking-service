package com.github.jon7even.setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jon7even.BankingServiceApp;
import com.github.jon7even.repository.BankAccountRepository;
import com.github.jon7even.repository.UserEmailRepository;
import com.github.jon7even.repository.UserPhoneRepository;
import com.github.jon7even.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
@SpringBootTest(classes = BankingServiceApp.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {"SERVER_EXTERNAL_PORT_APPLICATION_TEST=8097", "SCHEMA_APP=application"})
public class SetupControllerTest extends SetupContainerTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected UserRepository userRepository;
    @Autowired protected UserEmailRepository userEmailRepository;
    @Autowired protected UserPhoneRepository userPhoneRepository;
    @Autowired protected BankAccountRepository bankAccountRepository;
    @Autowired protected JdbcTemplate jdbcTemplate;

    @AfterEach
    protected void clearRepositoriesAnRestartSequences() {
        log.debug("Удаляем пользователей");
        userRepository.deleteAll();
        log.debug("Удаляем банковские аккаунты");
        bankAccountRepository.deleteAll();
        log.debug("Делаем сброс ID пользователей");
        jdbcTemplate.execute("ALTER SEQUENCE application.user_seq RESTART WITH 1");
        log.debug("Делаем сброс ID банковских аккаунтов");
        jdbcTemplate.execute("ALTER SEQUENCE application.bank_account_seq RESTART WITH 1");
        log.debug("Делаем сброс ID электронной почты");
        jdbcTemplate.execute("ALTER SEQUENCE application.email_seq RESTART WITH 1");
        log.debug("Делаем сброс ID номеров телефонов");
        jdbcTemplate.execute("ALTER SEQUENCE application.phone_seq RESTART WITH 1");
    }
}
