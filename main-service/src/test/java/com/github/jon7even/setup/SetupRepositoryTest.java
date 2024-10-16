package com.github.jon7even.setup;

import com.github.jon7even.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"SERVER_EXTERNAL_PORT_APPLICATION_TEST=8097", "SCHEMA_APP=application"})
public class SetupRepositoryTest extends SetupContainerTest {
    @Autowired protected UserRepository userRepository;
    @Autowired protected UserEmailRepository userEmailRepository;
    @Autowired protected UserPhoneRepository userPhoneRepository;
    @Autowired protected BankAccountRepository bankAccountRepository;
    @Autowired protected TransactionRepository transactionRepository;
    @Autowired protected JdbcTemplate jdbcTemplate;

    @BeforeEach protected void clearRepositoriesAndInitUserEntityAndRestartSequences() {
        initUserEntity();
        log.debug("Удаляем пользователей");
        userRepository.deleteAll();
        log.debug("Удаляем банковские аккаунты");
        bankAccountRepository.deleteAll();
        log.debug("Делаем сброс ID пользователей");
        transactionRepository.deleteAll();
        log.debug("Делаем сброс ID пользователей");
        jdbcTemplate.execute("ALTER SEQUENCE application.user_seq RESTART WITH 1");
        log.debug("Делаем сброс ID банковских аккаунтов");
        jdbcTemplate.execute("ALTER SEQUENCE application.bank_account_seq RESTART WITH 1");
        log.debug("Делаем сброс ID электронной почты");
        jdbcTemplate.execute("ALTER SEQUENCE application.email_seq RESTART WITH 1");
        log.debug("Делаем сброс ID номеров телефонов");
        jdbcTemplate.execute("ALTER SEQUENCE application.phone_seq RESTART WITH 1");
        log.debug("Делаем сброс ID транзакций");
        jdbcTemplate.execute("ALTER SEQUENCE application.transaction_seq RESTART WITH 1");
    }
}