package com.github.jon7even.bankingservice.setup;

import com.github.jon7even.bankingservice.repository.BankAccountRepository;
import com.github.jon7even.bankingservice.repository.UserEmailRepository;
import com.github.jon7even.bankingservice.repository.UserPhoneRepository;
import com.github.jon7even.bankingservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SetupRepositoryTest extends SetupContainerTest {
    @Autowired protected UserRepository userRepository;
    @Autowired protected UserEmailRepository userEmailRepository;
    @Autowired protected UserPhoneRepository userPhoneRepository;
    @Autowired protected BankAccountRepository bankAccountRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    @BeforeEach protected void clearRepositoriesAndInitUserEntity() {
        initUserEntity();
        userRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE application.user_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.email_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE application.phone_seq RESTART WITH 1");
    }
}