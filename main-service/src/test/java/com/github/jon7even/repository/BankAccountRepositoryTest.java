package com.github.jon7even.repository;

import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.setup.SetupRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BankAccountRepositoryTest extends SetupRepositoryTest {

    @DisplayName("[save] Должен создать банковский аккаунт для пользователя и присвоить ID")
    @Test public void shouldCreateNewBankAccountForUser_thenReturn_BankAccountEntityWithId() {
        userRepository.saveAndFlush(userEntityFirstWithoutId);
        BankAccountEntity actualBankAccountEntity = bankAccountRepository.save(bankAccountEntityFirstWithoutId);
        assertThat(actualBankAccountEntity)
                .isNotNull()
                .isEqualTo(bankAccountEntityFirst);
    }
}
