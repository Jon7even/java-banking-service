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
        when(userMapper.toEntityFromCreateDto(any(), any())).thenReturn(userEntityFirstWithoutId);
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(false);
        when(userRepository.existsByEmail(userCreateDtoFirst.getEmail())).thenReturn(false);
        when(userRepository.existsByPhone(userCreateDtoFirst.getPhone())).thenReturn(false);
        when(userRepository.save(userEntityFirstWithoutId)).thenReturn(userEntityFirst);
        when(userMapper.toFullDtoFromEntity(userEntityFirst)).thenReturn(userFullResponseDtoFirst);

        UserFullResponseDto actualResult = userService.createUser(userCreateDtoFirst);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userFullResponseDtoFirst);

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).existsByPhone(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [login] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionLoginAlreadyExist() {
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [email] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionEmailAlreadyExist() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, never()).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUser_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userRepository.existsByPhone(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(userRepository, times(1)).existsByPhone(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
