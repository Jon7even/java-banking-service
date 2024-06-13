package com.github.jon7even.service;

import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.service.impl.UserEmailServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserEmailServiceTest extends SetupServiceTest {
    @InjectMocks private UserEmailServiceImpl userEmailService;

    @BeforeEach public void setupServiceTest() {
        initUserEntity();
        initUserCreateDto();
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [email] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithEmail_thenReturn_ExceptionEmailAlreadyExist() {
        when(userEmailRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userEmailService.createNewEmails(
                userCreateDtoFirst.getEmails(), userEntityFirst
        ));

        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что список из [email] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithListEmails_thenReturn_ExceptionEmailAlreadyExist() {
        when(userEmailRepository.getEmailEntityBySetEmails(any())).thenReturn(listUserEmailEntitiesThird);

        assertThrows(IntegrityConstraintException.class, () -> userEmailService.createNewEmails(
                userCreateDtoThird.getEmails(), userEntityFirst
        ));

        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userEmailRepository, times(1)).getEmailEntityBySetEmails(anySet());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
    }
}
