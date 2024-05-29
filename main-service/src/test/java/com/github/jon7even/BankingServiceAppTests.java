package com.github.jon7even;

import com.github.jon7even.setup.SetupContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = BankingServiceApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(properties = {"SERVER_EXTERNAL_PORT_APPLICATION_TEST=8097", "SCHEMA_APP=application"})
class BankingServiceAppTests extends SetupContainerTest {

    @Test void contextLoads() {
    }

    @Test void testMain() {
        Assertions.assertDoesNotThrow(BankingServiceApp::new);
    }
}
