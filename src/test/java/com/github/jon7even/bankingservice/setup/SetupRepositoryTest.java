package com.github.jon7even.bankingservice.setup;

import com.github.jon7even.bankingservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SetupRepositoryTest extends SetupContainerTest {
    @Autowired protected UserRepository userRepository;

    @AfterEach protected void clearRepository() {
        userRepository.deleteAll();
    }
}
