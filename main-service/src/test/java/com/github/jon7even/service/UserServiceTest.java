package com.github.jon7even.service;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.service.impl.UserServiceImpl;
import com.github.jon7even.setup.SetupServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

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
        when(userMapper.toUserEntityFromCreateDto(any(UserCreateDto.class), any()))
                .thenReturn(userEntityFirstWithoutId);
        when(userMapper.toEntityBankAccountFromCreateDto(any(BankAccountCreateDto.class), any(UserEntity.class)))
                .thenReturn(bankAccountEntityFirstWithoutId);
        when(userMapper.toEntityEmailFromCreateDto(any()))
                .thenReturn(listUserEmailEntitiesFirstWithoutId);
        when(userMapper.toEntityPhoneFromCreateDto(any()))
                .thenReturn(listUserPhoneEntitiesFirstWithoutId);
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin()))
                .thenReturn(false);
        when(userEmailRepository.existsByEmail(userCreateDtoFirst.getEmails().stream().findFirst().get().getEmail()))
                .thenReturn(false);
        when(userPhoneRepository.existsByPhone(userCreateDtoFirst.getPhones().stream().findFirst().get().getPhone()))
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
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, times(1)).existsByPhone(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
        verify(bankAccountRepository, times(1)).saveAndFlush(any(BankAccountEntity.class));
        verify(userEmailRepository, times(1)).saveAllAndFlush(any());
        verify(userPhoneRepository, times(1)).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться, [balance] не может быть отрицательным")
    @Test public void shouldNotCreateNewUserWithBadBalance_thenReturn_ExceptionIncorrectMadeRequest() {
        bankAccountCreateDtoFirst.setBalance(BigDecimal.valueOf(-1.01));
        userCreateDtoFirst.setBankAccountCreateDto(bankAccountCreateDtoFirst);

        assertThrows(IncorrectMadeRequestException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, never()).existsByLogin(anyString());
        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(anySet());
        verify(userPhoneRepository, never()).saveAllAndFlush(anySet());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [login] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithLoginExists_thenReturn_ExceptionLoginAlreadyExist() {
        when(userRepository.existsByLogin(userCreateDtoFirst.getLogin())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(userCreateDtoFirst.getLogin());
        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [email] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithEmail_thenReturn_ExceptionEmailAlreadyExist() {
        when(userEmailRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что такой [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithPhone_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userPhoneRepository.existsByPhone(any())).thenReturn(true);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoFirst));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, times(1)).existsByEmail(anyString());
        verify(userPhoneRepository, times(1)).existsByPhone(anyString());
        verify(userEmailRepository, never()).getEmailEntityBySetEmails(any());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что список из [email] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithListEmails_thenReturn_ExceptionEmailAlreadyExist() {
        when(userEmailRepository.getEmailEntityBySetEmails(any())).thenReturn(listUserEmailEntitiesThird);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoThird));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userEmailRepository, times(1)).getEmailEntityBySetEmails(anySet());
        verify(userPhoneRepository, never()).getPhoneEntityBySetPhones(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }

    @DisplayName("[createUser] Новый пользователь не должен создаться потому что список из [phone] уже есть в БД")
    @Test public void shouldNotCreateNewUserWithListPhones_thenReturn_ExceptionPhoneAlreadyExist() {
        when(userPhoneRepository.getPhoneEntityBySetPhones(any())).thenReturn(listUserPhoneEntitiesThird);

        assertThrows(IntegrityConstraintException.class, () -> userService.createUser(userCreateDtoThird));

        verify(userRepository, times(1)).existsByLogin(anyString());
        verify(userEmailRepository, never()).existsByEmail(anyString());
        verify(userPhoneRepository, never()).existsByPhone(anyString());
        verify(userEmailRepository, times(1)).getEmailEntityBySetEmails(anySet());
        verify(userPhoneRepository, times(1)).getPhoneEntityBySetPhones(anySet());
        verify(userRepository, never()).saveAndFlush(any());
        verify(bankAccountRepository, never()).saveAndFlush(any());
        verify(userEmailRepository, never()).saveAllAndFlush(any());
        verify(userPhoneRepository, never()).saveAllAndFlush(any());
    }
}
