package com.github.jon7even.service.impl;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.UserShortResponseDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.enums.user.UserSearchType;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.mapper.BankAccountMapper;
import com.github.jon7even.mapper.UserEmailMapper;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.mapper.UserPhoneMapper;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.BankAccountService;
import com.github.jon7even.service.UserEmailService;
import com.github.jon7even.service.UserPhoneService;
import com.github.jon7even.service.UserService;
import com.github.jon7even.utils.ConverterPageable;
import com.github.jon7even.utils.ConverterSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.jon7even.constants.LogsMessage.*;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

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
    private final UserEmailService userEmailService;
    private final UserPhoneService userPhoneService;
    private final BankAccountService bankAccountService;
    private final UserMapper userMapper;
    private final BankAccountMapper bankAccountMapper;
    private final UserEmailMapper userEmailMapper;
    private final UserPhoneMapper userPhoneMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public UserFullResponseDto createUser(UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        log.trace(SAVE_IN_REPOSITORY + "[userCreateDto={}]", userCreateDto);
        checkUserCreateDto(userCreateDto);
        UserEntity userForSaveInRepository = userMapper.toUserEntityFromCreateDto(userCreateDto, LocalDateTime.now());

        UserEntity savedUserFromRepository = userRepository.saveAndFlush(userForSaveInRepository);

        BankAccountShortResponseDto savedBankAccountInRepository = bankAccountService.createBankAccount(
                userCreateDto.getBankAccount(), savedUserFromRepository
        );

        List<EmailShortResponseDto> savedUserEmailsInRepository = userEmailService.createNewEmails(
                userCreateDto.getEmails(), savedUserFromRepository
        );

        List<PhoneShortResponseDto> savedUserPhonesInRepository = userPhoneService.createNewPhones(
                userCreateDto.getPhones(), savedUserFromRepository
        );
        log.trace("У нас успешно зарегистрирован новый пользователь [user={}]", savedUserFromRepository);
        return userMapper.toUserFullDtoFromUserEntity(savedUserFromRepository,
                savedBankAccountInRepository,
                savedUserEmailsInRepository,
                savedUserPhonesInRepository
        );
    }

    @Override
    public List<UserShortResponseDto> getListUsersByParam(ParamsSearchUserRequestDto paramsSearchUserRequestDto) {
        log.trace("Начинаем получать список пользователей по запросу: {}", paramsSearchUserRequestDto);
        Pageable pageable = getPageableFromParamsSearchUserRequestDto(paramsSearchUserRequestDto);
        UserSearchType userSearchType = getUserSearchTypeByParamsSearchUserRequestDto(paramsSearchUserRequestDto);

        List<UserEntity> listOfUsersFromRepository = getListUserEntityFromRepository(
                paramsSearchUserRequestDto, userSearchType, pageable);
        log.trace("Получили список из [size={}] пользователей", listOfUsersFromRepository.size());

        return listOfUsersFromRepository.stream()
                .map((userEntity -> userMapper.toUserShortDtoFromUserEntity(userEntity,
                        bankAccountMapper.toShortBalanceDtoFromBankAccountEntity(userEntity.getBankAccountEntity()),
                        userEmailMapper.toShortListEmailDtoFromEmailEntity(userEntity.getEmails()),
                        userPhoneMapper.toShortListPhoneDtoFromPhoneEntity(userEntity.getPhones()))
                ))
                .collect(Collectors.toList());
    }

    private List<UserEntity> getListUserEntityFromRepository(ParamsSearchUserRequestDto paramsSearchUserRequestDto,
                                                             UserSearchType userSearchType,
                                                             Pageable pageable) {
        log.debug("Начинаем получать список пользователей из БД [параметры={}], [страницы и сортировка={}], [тип={}]",
                paramsSearchUserRequestDto, pageable, userSearchType);
        List<UserEntity> listOfUserEntitiesFromRepository;
        switch (userSearchType) {
            case GET_BY_NAME -> listOfUserEntitiesFromRepository =
                    userRepository.getListUserByLikeName(paramsSearchUserRequestDto.getFirstName(),
                            paramsSearchUserRequestDto.getLastName(),
                            paramsSearchUserRequestDto.getMiddleName(),
                            pageable);
            case GET_BY_PHONE -> listOfUserEntitiesFromRepository =
                    userRepository.getListUserByPhone(paramsSearchUserRequestDto.getPhone(), pageable);
            case GET_BY_EMAIL -> listOfUserEntitiesFromRepository =
                    userRepository.getListUserByEmail(paramsSearchUserRequestDto.getEmail(), pageable);
            case GET_BY_DATE_OF_BIRTH -> listOfUserEntitiesFromRepository =
                    userRepository.getListUserByAfterParamDate(paramsSearchUserRequestDto.getDateOfBirth(), pageable);
            default -> listOfUserEntitiesFromRepository = userRepository.findAll(pageable).getContent();
        }
        return listOfUserEntitiesFromRepository;
    }

    private UserSearchType getUserSearchTypeByParamsSearchUserRequestDto(ParamsSearchUserRequestDto paramsDto) {
        log.debug("Начинаем определять тип поиска по запросу: {}", paramsDto);
        if (paramsDto.getEmail() != null && !paramsDto.getEmail().isBlank()) {
            log.debug("{} [{}]", SEARCH_BY, PARAMETER_EMAIL);
            return UserSearchType.GET_BY_EMAIL;
        }

        if (paramsDto.getPhone() != null && !paramsDto.getPhone().isBlank()) {
            log.debug("{} [{}]", SEARCH_BY, PARAMETER_PHONE);
            return UserSearchType.GET_BY_PHONE;
        }

        if (paramsDto.getDateOfBirth() != null) {
            log.debug("{} [{}]", SEARCH_BY, PARAMETER_DATE_OF_BIRTH);
            return UserSearchType.GET_BY_DATE_OF_BIRTH;
        }

        if (paramsDto.getFirstName() != null && !paramsDto.getFirstName().isBlank()
                || paramsDto.getLastName() != null && !paramsDto.getLastName().isBlank()
                || paramsDto.getMiddleName() != null && !paramsDto.getMiddleName().isBlank()) {
            log.debug("{} [{}]", SEARCH_BY, PARAMETER_FULL_NAME);
            return UserSearchType.GET_BY_NAME;
        }

        log.debug("{} [без ключевых параметров, поиск по всем пользователям]", SEARCH_BY);
        return UserSearchType.GET_BY_NO_PARAMETERS;
    }

    private Pageable getPageableFromParamsSearchUserRequestDto(ParamsSearchUserRequestDto paramsSearchUserRequestDto) {
        log.debug("Начинаем конвертировать пагинацию из запроса: {}", paramsSearchUserRequestDto);
        Optional<Sort> sortOptional = ConverterSort.getSortingByParamsSearchUserRequest(paramsSearchUserRequestDto);
        return ConverterPageable.getPageRequestByParams(
                paramsSearchUserRequestDto.getFrom(), paramsSearchUserRequestDto.getSize(), sortOptional
        );
    }

    private void checkUserCreateDto(UserCreateDto userCreateDto) {
        log.debug("{} поля нового пользователя перед сохранением в БД", START_CHECKING);
        var balance = userCreateDto.getBankAccount().getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            log.error(PARAMETER_BAD_REQUEST + PARAMETER_BALANCE + WRONG_CAN_NOT + "отрицательным");
            throw new IncorrectMadeRequestException(PARAMETER_BALANCE, WRONG_CAN_NOT + "отрицательным");
        }

        var login = userCreateDto.getLogin();
        if (existsUserByLogin(login)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_LOGIN, login);
            throw new IntegrityConstraintException(PARAMETER_LOGIN, login + NOTE_ALREADY_EXIST);
        }
        log.debug("Проверка успешно завершена, пользователя можно сохранять");
    }

    private boolean existsUserByLogin(String login) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_LOGIN, login);
        return userRepository.existsByLogin(login);
    }
}
