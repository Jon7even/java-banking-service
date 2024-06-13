package com.github.jon7even.service;

import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.service.impl.BankAccountServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BankAccountServiceTest extends SetupServiceTest {
    @InjectMocks private BankAccountServiceImpl bankAccountService;

    @BeforeEach public void setupServiceTest() {
        initUserEntity();
        initUserCreateDto();
    }

    @DisplayName("[createBankAccount] Новый аккаунт не должен создаться, [balance] не может быть отрицательным")
    @Test public void shouldNotCreateNewBankAccountWithBadBalance_thenReturn_ExceptionIncorrectMadeRequest() {
        bankAccountCreateDtoFirst.setBalance(BigDecimal.valueOf(-1.01));
        userCreateDtoFirst.setBankAccount(bankAccountCreateDtoFirst);

        assertThrows(IncorrectMadeRequestException.class, () -> bankAccountService.createBankAccount(
                userCreateDtoFirst.getBankAccount(), userEntityFirst
        ));

        verify(userRepository, never()).existsByLogin(anyString());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
    }
}
