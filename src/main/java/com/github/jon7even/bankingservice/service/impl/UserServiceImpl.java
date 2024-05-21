package com.github.jon7even.bankingservice.service.impl;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.exception.IntegrityConstraintException;
import com.github.jon7even.bankingservice.mapper.UserMapper;
import com.github.jon7even.bankingservice.repository.UserRepository;
import com.github.jon7even.bankingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.github.jon7even.bankingservice.constants.LogsMessage.*;

/**
 * Реализация сервиса взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserFullResponseDto createUser(UserCreateDto userCreateDto) {
        log.trace(SAVE_IN_REPOSITORY + "[userCreateDto={}]", userCreateDto);
        checkUserCreateDto(userCreateDto);
        UserEntity userForSaveInRepository = userMapper.toEntityFromCreateDto(userCreateDto, LocalDateTime.now());
        UserEntity savedUserFromRepository = userRepository.save(userForSaveInRepository);
        log.trace("У нас успешно зарегистрирован новый пользователь [user={}]", savedUserFromRepository);
        return userMapper.toFullDtoFromEntity(savedUserFromRepository);
    }

    private void checkUserCreateDto(UserCreateDto userCreateDto) {
        log.debug("Начинаем проверять поля нового пользователя перед сохранением в БД");
        var login = userCreateDto.getLogin();
        if (existsUserByLogin(login)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[логин={}]", login);
            throw new IntegrityConstraintException("логин", login);
        }

        var email = userCreateDto.getEmail();
        if (existsUserByEmail(email)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[почта={}]", email);
            throw new IntegrityConstraintException("почта", email);
        }

        var phone = userCreateDto.getPhone();
        if (existsUserByPhone(phone)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[телефон={}]", phone);
            throw new IntegrityConstraintException("телефон", phone);
        }
        log.debug("Проверка успешно завершена, пользователя можно сохранять");
    }

    private boolean existsUserByLogin(String login) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[логин={}]", login);
        return userRepository.existsByLogin(login);
    }

    private boolean existsUserByEmail(String email) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[почта={}]", email);
        return userRepository.existsByEmail(email);
    }

    private boolean existsUserByPhone(String phone) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[телефон={}]", phone);
        return userRepository.existsByPhone(phone);
    }
}
