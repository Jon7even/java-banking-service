package com.github.jon7even.service;

import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.service.impl.UserPhoneServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserPhoneServiceTest extends SetupServiceTest {
    @InjectMocks private UserPhoneServiceImpl userPhoneService;

    @BeforeEach public void setupServiceTest() {
        initUserEntity();
        initUserCreateDto();
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithPhone_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userPhoneRepository.existsByPhone(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userPhoneService.createNewPhones(
                userCreateDtoFirst.getPhones(), userEntityFirst
        ));

        verify(userPhoneRepository, times(1)).existsByPhone(anyString());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что список из [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithListPhones_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userPhoneRepository.getPhoneEntityBySetPhones(any())).thenReturn(listUserPhoneEntitiesThird);

        assertThrows(IntegrityConstraintException.class, () -> userPhoneService.createNewPhones(
                userCreateDtoThird.getPhones(), userEntityFirst
        ));

        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userPhoneRepository, times(1)).getPhoneEntityBySetPhones(anySet());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }
}