package com.github.jon7even.bankingservice;

import com.github.jon7even.bankingservice.setup.SetupContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = BankingServiceApp.class)
class BankingServiceAppTests extends SetupContainerTest {
    @Test void contextLoads() {
    }

    @Test void testMain() {
        Assertions.assertDoesNotThrow(BankingServiceApp::new);
    }
}
