package com.github.jon7even.bankingservice.service.impl;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.dto.user.UserShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailCreateDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.bankingservice.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.bankingservice.entity.BankAccountEntity;
import com.github.jon7even.bankingservice.entity.UserEmailEntity;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.entity.UserPhoneEntity;
import com.github.jon7even.bankingservice.enums.user.UserSearchType;
import com.github.jon7even.bankingservice.exception.IncorrectMadeRequestException;
import com.github.jon7even.bankingservice.exception.IntegrityConstraintException;
import com.github.jon7even.bankingservice.mapper.UserMapper;
import com.github.jon7even.bankingservice.repository.BankAccountRepository;
import com.github.jon7even.bankingservice.repository.UserEmailRepository;
import com.github.jon7even.bankingservice.repository.UserPhoneRepository;
import com.github.jon7even.bankingservice.repository.UserRepository;
import com.github.jon7even.bankingservice.service.UserService;
import com.github.jon7even.bankingservice.utils.ConverterPageable;
import com.github.jon7even.bankingservice.utils.ConverterSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final BankAccountRepository bankAccountRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserFullResponseDto createUser(UserCreateDto userCreateDto) {
        log.trace(SAVE_IN_REPOSITORY + "[userCreateDto={}]", userCreateDto);
        checkUserCreateDto(userCreateDto);
        UserEntity userForSaveInRepository = userMapper.toUserEntityFromCreateDto(userCreateDto, LocalDateTime.now());
        UserEntity savedUserFromRepository = userRepository.saveAndFlush(userForSaveInRepository);

        BankAccountEntity bankAccountEntityForSaveInRepository = userMapper.toEntityBankAccountFromCreateDto(
                userCreateDto.getBankAccountCreateDto(), savedUserFromRepository
        );
        BankAccountEntity savedBankAccountEntityInRepository =
                bankAccountRepository.saveAndFlush(bankAccountEntityForSaveInRepository);

        List<UserEmailEntity> userEmailEntitiesForSaveInRepository = userMapper.toEntityEmailFromCreateDto(
                userCreateDto.getEmails().stream().toList()
        );
        userEmailEntitiesForSaveInRepository.forEach(userEmail -> userEmail.setOwner(savedUserFromRepository));
        List<UserEmailEntity> savedUserEmailEntityInRepository =
                userEmailRepository.saveAllAndFlush(userEmailEntitiesForSaveInRepository);

        List<UserPhoneEntity> userPhoneEntitiesForSaveInRepository = userMapper.toEntityPhoneFromCreateDto(
                userCreateDto.getPhones().stream().toList()
        );
        userPhoneEntitiesForSaveInRepository.forEach(userPhone -> userPhone.setOwner(savedUserFromRepository));
        List<UserPhoneEntity> savedUserPhoneEntityInRepository =
                userPhoneRepository.saveAllAndFlush(userPhoneEntitiesForSaveInRepository);

        log.trace("У нас успешно зарегистрирован новый пользователь [user={}]", savedUserFromRepository);
        return userMapper.toUserFullDtoFromUserEntity(savedUserFromRepository,
                userMapper.toShortBalanceDtoFromBankAccountEntity(savedBankAccountEntityInRepository),
                userMapper.toShortEmailDtoFromEmailEntity(savedUserEmailEntityInRepository),
                userMapper.toShortPhoneDtoFromPhoneEntity(savedUserPhoneEntityInRepository)
        );
    }

    @Override
    public List<UserShortResponseDto> getListUsersByParam(ParamsSearchUserRequestDto paramsSearchUserRequestDto) {
        log.debug("Начинаем получать список пользователей по запросу: {}", paramsSearchUserRequestDto);
        Pageable pageable = getPageableFromParamsSearchUserRequestDto(paramsSearchUserRequestDto);
        UserSearchType userSearchType = getUserSearchTypeByParamsSearchUserRequestDto(paramsSearchUserRequestDto);
        List<UserEntity> listOfUsersFromRepository = getListUserEntityFromRepository(
                paramsSearchUserRequestDto, userSearchType, pageable);
        log.debug("Получили список из [size={}] пользователей", listOfUsersFromRepository.size());
        return listOfUsersFromRepository.stream()
                .map((userEntity -> userMapper.toUserShortDtoFromUserEntity(userEntity,
                        userMapper.toShortBalanceDtoFromBankAccountEntity(userEntity.getBankAccountEntity()),
                        userMapper.toShortEmailDtoFromEmailEntity(userEntity.getEmails()),
                        userMapper.toShortPhoneDtoFromPhoneEntity(userEntity.getPhones()))
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
        log.debug("Начинаем проверять поля нового пользователя перед сохранением в БД");
        var login = userCreateDto.getLogin();
        if (existsUserByLogin(login)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_LOGIN, login);
            throw new IntegrityConstraintException(PARAMETER_LOGIN, login);
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
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, emails);
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
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}}={}]", PARAMETER_PHONE, phone.getPhone());
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
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_LOGIN, login);
        return userRepository.existsByLogin(login);
    }

    private boolean existsEmailByStringEmail(String email) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, email);
        return userEmailRepository.existsByEmail(email);
    }

    private boolean existsPhoneByStringPhone(String phone) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phone);
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
