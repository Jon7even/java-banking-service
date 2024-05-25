package com.github.jon7even.bankingservice.service.impl;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailCreateDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.bankingservice.entity.UserEmailEntity;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.entity.UserPhoneEntity;
import com.github.jon7even.bankingservice.exception.IncorrectMadeRequestException;
import com.github.jon7even.bankingservice.exception.IntegrityConstraintException;
import com.github.jon7even.bankingservice.mapper.UserMapper;
import com.github.jon7even.bankingservice.repository.UserEmailRepository;
import com.github.jon7even.bankingservice.repository.UserPhoneRepository;
import com.github.jon7even.bankingservice.repository.UserRepository;
import com.github.jon7even.bankingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private final UserEmailRepository userEmailRepository;
    private final UserPhoneRepository userPhoneRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserFullResponseDto createUser(UserCreateDto userCreateDto) {
        log.trace(SAVE_IN_REPOSITORY + "[userCreateDto={}]", userCreateDto);
        checkUserCreateDto(userCreateDto);

        List<UserEmailEntity> userEmailEntities =
                userMapper.toEntityEmailFromCreateDto(userCreateDto.getEmails().stream().toList());
        List<UserPhoneEntity> userPhoneEntities =
                userMapper.toEntityPhoneFromCreateDto(userCreateDto.getPhones().stream().toList());
        UserEntity userForSaveInRepository = userMapper.toUserEntityFromCreateDto(userCreateDto, LocalDateTime.now());

        UserEntity savedUserFromRepository = userRepository.save(userForSaveInRepository);

        userEmailEntities.forEach(userEmailEntity -> userEmailEntity.setOwner(savedUserFromRepository));
        userEmailRepository.saveAllAndFlush(userEmailEntities);

        userPhoneEntities.forEach(userPhoneEntity -> userPhoneEntity.setOwner(savedUserFromRepository));
        userPhoneRepository.saveAllAndFlush(userPhoneEntities);

        log.trace("У нас успешно зарегистрирован новый пользователь [user={}]", savedUserFromRepository);
        return userMapper.toUserFullDtoFromUserEntity(savedUserFromRepository,
                userMapper.toShortEmailDtoFromEmailEntity(savedUserFromRepository.getEmails()),
                userMapper.toShortPhoneDtoFromPhoneEntity(savedUserFromRepository.getPhones())
        );
    }

    private void checkUserCreateDto(UserCreateDto userCreateDto) {
        log.debug("Начинаем проверять поля нового пользователя перед сохранением в БД");
        var login = userCreateDto.getLogin();
        if (existsUserByLogin(login)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[логин={}]", login);
            throw new IntegrityConstraintException("логин", login);
        }

        checkEmailCreateDto(userCreateDto.getEmails());
        checkPhoneCreateDto(userCreateDto.getPhones());

        log.debug("Проверка успешно завершена, пользователя можно сохранять");
    }

    private void checkEmailCreateDto(Set<EmailCreateDto> emails) {
        if (emails.size() == 1) {
            var email = emails.stream().findFirst()
                    .orElseThrow(() -> new IntegrityConstraintException(PARAMETER_EMAIL, emails.toString()));
            if (existsEmailByStringEmail(email.getEmail())) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[адрес электронной почты={}]", email.getEmail());
                throw new IntegrityConstraintException(PARAMETER_EMAIL, email.getEmail());
            }
        } else if (emails.size() > 1) {
            if (existsEmailBySetEmails(Collections.singleton(emails.toString()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[список адресов электронной почты={}]", emails);
                throw new IntegrityConstraintException(PARAMETER_EMAIL, emails.toString());
            }
        } else {
            log.error(PARAMETER_BAD_REQUEST + "список [email адресов] оказался пуст");
            throw new IncorrectMadeRequestException(PARAMETER_EMAIL, "не может быть пустым");
        }
    }

    private void checkPhoneCreateDto(Set<PhoneCreateDto> phones) {
        if (phones.size() == 1) {
            var phone = phones.stream().findFirst()
                    .orElseThrow(() -> new IntegrityConstraintException(PARAMETER_PHONE, phones.toString()));
            if (existsPhoneByStringPhone(phone.getPhone())) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[номер телефона={}]", phone.getPhone());
                throw new IntegrityConstraintException(PARAMETER_PHONE, phone.getPhone());
            }
        } else if (phones.size() > 1) {
            if (existsPhoneBySetPhones(Collections.singleton(phones.toString()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[список номеров={}]", phones);
                throw new IntegrityConstraintException(PARAMETER_PHONE, phones.toString());
            }
        } else {
            log.error(PARAMETER_BAD_REQUEST + "список [номеров] оказался пуст");
            throw new IncorrectMadeRequestException(PARAMETER_PHONE, "не может быть пустым");
        }
    }

    private boolean existsUserByLogin(String login) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[логин={}]", login);
        return userRepository.existsByLogin(login);
    }

    private boolean existsEmailByStringEmail(String email) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[почта={}]", email);
        return userEmailRepository.existsByEmail(email);
    }

    private boolean existsPhoneByStringPhone(String phone) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[телефон={}]", phone);
        return userPhoneRepository.existsByPhone(phone);
    }

    private boolean existsEmailBySetEmails(Set<String> emails) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[список адресов={}]", emails);
        var listOfEmails = userEmailRepository.getEmailEntityBySetEmails(emails);
        return !listOfEmails.isEmpty();
    }

    private boolean existsPhoneBySetPhones(Set<String> phones) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[список номеров телефонов={}]", phones);
        var listOfPhones = userPhoneRepository.getPhoneEntityBySetPhones(phones);
        return !listOfPhones.isEmpty();
    }
}
