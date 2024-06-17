package com.github.jon7even;

import com.github.jon7even.setup.SetupContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = BankingServiceApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(properties = {"SERVER_EXTERNAL_PORT_APPLICATION_TEST=8097", "SCHEMA_APP=application",
        "SECURITY_TOKEN_KEY_TEST=2kjhgO987TG969876RF98ftGR8765DE465877908y90GOIUF8676RF86",
        "SECURITY_TOKEN_LIFETIME_TEST=1800000"})
class BankingServiceAppTests extends SetupContainerTest {

    @Test void contextLoads() {
    }

    @Test void testMain() {
        Assertions.assertDoesNotThrow(BankingServiceApp::new);
    }
}
