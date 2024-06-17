package com.github.jon7even.service;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.service.impl.BankAccountServiceImpl;
import com.github.jon7even.service.impl.UserEmailServiceImpl;
import com.github.jon7even.service.impl.UserPhoneServiceImpl;
import com.github.jon7even.service.impl.UserServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends SetupServiceTest {
    @InjectMocks private UserServiceImpl userService;
    @Mock private UserEmailServiceImpl userEmailService;
    @Mock private UserPhoneServiceImpl userPhoneService;
    @Mock private BankAccountServiceImpl bankAccountService;

    @BeforeEach public void setupServiceTest() {
        initUserEntity();
        initUserCreateDto();
        initUserFullResponseDto();
    }

    @DisplayName("[createUser] Новый пользователь должен создаться с релевантными полями")
    @Test public void shouldCreateNewUser_thenReturn_UserFullResponseDto() {
        when(userMapper.toUserEntityFromCreateDto(any(UserCreateDto.class), any()))
                .thenReturn(userEntityFirstWithoutId);
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin()))
                .thenReturn(false);
        when(userMapper.toUserFullDtoFromUserEntity(any(UserEntity.class), any(), any(), any()))
                .thenReturn(userFullResponseDtoFirst);
        when(userRepository.saveAndFlush(any()))
                .thenReturn(userEntityFirst);
        when(bankAccountService.createBankAccount(any(), any()))
                .thenReturn(bankAccountFullResponseDto);

        UserFullResponseDto actualResult = userService.createUser(userCreateDtoFirst);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userFullResponseDtoFirst);

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
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
