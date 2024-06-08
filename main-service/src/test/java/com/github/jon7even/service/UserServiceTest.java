package com.github.jon7even.service;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.service.impl.UserEmailServiceImpl;
import com.github.jon7even.service.impl.UserPhoneServiceImpl;
import com.github.jon7even.service.impl.UserServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends SetupServiceTest {
    @InjectMocks private UserServiceImpl userService;
    @Mock private UserEmailServiceImpl userEmailService;
    @Mock private UserPhoneServiceImpl userPhoneService;

    @BeforeEach public void setupMapperTest() {
        initUserEntity();
        initUserCreateDto();
        initUserFullResponseDto();
    }

    @DisplayName("[createUser] Новый пользователь должен создаться с релевантными полями")
    @Test public void shouldCreateNewUser_thenReturn_UserFullResponseDto() {
        when(userMapper.toUserEntityFromCreateDto(any(UserCreateDto.class), any()))
                .thenReturn(userEntityFirstWithoutId);
        when(bankAccountMapper.toEntityBankAccountFromCreateDto(any(BankAccountCreateDto.class), any(UserEntity.class)))
                .thenReturn(bankAccountEntityFirstWithoutId);
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin()))
                .thenReturn(false);
        when(userMapper.toUserFullDtoFromUserEntity(any(UserEntity.class), any(), any(), any()))
                .thenReturn(userFullResponseDtoFirst);
        when(userRepository.saveAndFlush(any()))
                .thenReturn(userEntityFirst);

        UserFullResponseDto actualResult = userService.createUser(userCreateDtoFirst);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userFullResponseDtoFirst);

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
        verify(bankAccountRepository, times(1)).saveAndFlush(any(BankAccountEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться, [balance] не может быть отрицательным")
    @Test public void shouldNotCreateNewUserWithBadBalance_thenReturn_ExceptionIncorrectMadeRequest() {
        bankAccountCreateDtoFirst.setBalance(BigDecimal.valueOf(-1.01));
        userCreateDtoFirst.setBankAccount(bankAccountCreateDtoFirst);

        assertThrows(IncorrectMadeRequestException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, never()).existsByLogin(anyString());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [login] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithLoginExists_thenReturn_ExceptionLoginAlreadyExist() {
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(userCreateDtoFirst.getLogin());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
    }
}
