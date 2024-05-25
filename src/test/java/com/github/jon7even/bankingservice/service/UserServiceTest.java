package com.github.jon7even.bankingservice.service;

import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.exception.IntegrityConstraintException;
import com.github.jon7even.bankingservice.service.impl.UserServiceImpl;
import com.github.jon7even.bankingservice.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest extends SetupServiceTest {
    @InjectMocks private UserServiceImpl userService;

    @BeforeEach public void setupMapperTest() {
        initUserEntity();
        initUserCreateDto();
        initUserFullResponseDto();
    }

    @DisplayName("[createUser] Новый пользователь должен создаться с релевантными полями")
    @Test public void shouldCreateNewUser_thenReturn_UserFullResponseDto() {
        when(userMapper.toUserEntityFromCreateDto(any(), any())).thenReturn(userEntityFirstWithoutId);
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(false);
        when(userEmailRepository.existsByEmail(any())).thenReturn(false);
        when(userPhoneRepository.existsByPhone(any())).thenReturn(false);
        when(userRepository.save(userEntityFirstWithoutId)).thenReturn(userEntityFirst);
        when(userMapper.toUserFullDtoFromUserEntity(any(), any(), any())).thenReturn(userFullResponseDtoFirst);

        UserFullResponseDto actualResult = userService.createUser(userCreateDtoFirst);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userFullResponseDtoFirst);

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, times(1)).existsByPhone(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [login] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionLoginAlreadyExist() {
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [email] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionEmailAlreadyExist() {
        when(userEmailRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userPhoneRepository.existsByPhone(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, times(1)).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
