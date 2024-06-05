package com.github.jon7even.service.impl;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.UserShortResponseDto;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.email.EmailUpdateDto;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneUpdateDto;
import com.github.jon7even.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.entity.UserPhoneEntity;
import com.github.jon7even.enums.user.UserSearchType;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.EntityNotFoundException;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.repository.BankAccountRepository;
import com.github.jon7even.repository.UserEmailRepository;
import com.github.jon7even.repository.UserPhoneRepository;
import com.github.jon7even.repository.UserRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.jon7even.constants.LogsMessage.*;

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
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserFullResponseDto createUser(UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        log.trace(SAVE_IN_REPOSITORY + "[userCreateDto={}]", userCreateDto);
        checkUserCreateDto(userCreateDto);
        UserEntity userForSaveInRepository = userMapper.toUserEntityFromCreateDto(userCreateDto, LocalDateTime.now());
        UserEntity savedUserFromRepository = userRepository.saveAndFlush(userForSaveInRepository);

        BankAccountEntity bankAccountEntityForSaveInRepository = userMapper.toEntityBankAccountFromCreateDto(
                userCreateDto.getBankAccount(), savedUserFromRepository
        );
        BankAccountEntity savedBankAccountEntityInRepository =
                bankAccountRepository.saveAndFlush(bankAccountEntityForSaveInRepository);

        List<UserEmailEntity> userEmailEntitiesForSaveInRepository = userMapper.toListEntityEmailFromCreateDto(
                userCreateDto.getEmails().stream().toList()
        );
        userEmailEntitiesForSaveInRepository.forEach(userEmail -> userEmail.setOwner(savedUserFromRepository));
        List<UserEmailEntity> savedUserEmailEntityInRepository = userEmailRepository.saveAllAndFlush(
                sortListUserEmailsByLexicographic(userEmailEntitiesForSaveInRepository)
        );

        List<UserPhoneEntity> userPhoneEntitiesForSaveInRepository = userMapper.toListEntityPhoneFromCreateDto(
                userCreateDto.getPhones().stream().toList()
        );
        userPhoneEntitiesForSaveInRepository.forEach(userPhone -> userPhone.setOwner(savedUserFromRepository));
        List<UserPhoneEntity> savedUserPhoneEntityInRepository = userPhoneRepository.saveAllAndFlush(
                sortListUserPhonesByLexicographic(userPhoneEntitiesForSaveInRepository)
        );

        log.trace("У нас успешно зарегистрирован новый пользователь [user={}]", savedUserFromRepository);
        return userMapper.toUserFullDtoFromUserEntity(savedUserFromRepository,
                userMapper.toShortBalanceDtoFromBankAccountEntity(savedBankAccountEntityInRepository),
                userMapper.toShortListEmailDtoFromEmailEntity(sortListUserEmailsId(savedUserEmailEntityInRepository)),
                userMapper.toShortListPhoneDtoFromPhoneEntity(sortListUserPhonesId(savedUserPhoneEntityInRepository))
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
                        userMapper.toShortListEmailDtoFromEmailEntity(userEntity.getEmails()),
                        userMapper.toShortListPhoneDtoFromPhoneEntity(userEntity.getPhones()))
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EmailShortResponseDto addNewEmail(EmailCreateDto emailCreateDto, Long userId) {
        log.debug(SAVE_IN_REPOSITORY + "[emailCreateDto={}] для [userId={}]", emailCreateDto, userId);
        if (existsEmailByStringEmail(emailCreateDto.getEmail())) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, emailCreateDto.getEmail());
            throw new IntegrityConstraintException(PARAMETER_EMAIL, emailCreateDto.getEmail() + NOTE_ALREADY_EXIST);
        }
        UserEntity ownerEmail = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с [userId=%d]", userId)));
        UserEmailEntity userEmailEntityForSave = userMapper.toEntityEmailFromCreateDto(emailCreateDto, ownerEmail);

        UserEmailEntity savedUserEmailEntityInRepository = userEmailRepository.saveAndFlush(userEmailEntityForSave);

        return userMapper.toShortEmailDtoFromEmailEntity(savedUserEmailEntityInRepository);
    }

    @Override
    public EmailShortResponseDto updateEmailById(EmailUpdateDto emailUpdateDto, Long userId) {
        log.debug(UPDATE_IN_REPOSITORY + "[emailUpdateDto={}] для [userId={}]", emailUpdateDto, userId);
        if (existsEmailByStringEmail(emailUpdateDto.getEmail())) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, emailUpdateDto.getEmail());
            throw new IntegrityConstraintException(PARAMETER_EMAIL, emailUpdateDto.getEmail() + NOTE_ALREADY_EXIST);
        }
        var emailId = emailUpdateDto.getId();
        UserEmailEntity userEmailEntityFromRepository = userEmailRepository.findById(emailId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Email с [emailId=%d]", emailId)));

        if (userEmailEntityFromRepository.getOwner().getId().equals(userId)) {
            userEmailEntityFromRepository.setEmail(emailUpdateDto.getEmail());
            UserEmailEntity updatedEmail = userEmailRepository.saveAndFlush(userEmailEntityFromRepository);
            log.debug("В БД произошло обновление электронной почты: [{}]", updatedEmail);
            return userMapper.toShortEmailDtoFromEmailEntity(updatedEmail);
        } else {
            log.error("Произошел инцидент: [{}] не является владельцем электронной почты: [{}]", userId, emailId);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_PHONE));
        }
    }

    @Override
    public void deleteEmailById(Long userId, Long emailId) {
        log.debug(DELETE_IN_REPOSITORY + "[emailId={}] для [userId={}]", emailId, userId);
        UserEmailEntity userEmailEntityFromRepository = userEmailRepository.findById(emailId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Email с [emailId=%d]", emailId)));

        if (userEmailEntityFromRepository.getOwner().getId().equals(userId)) {
            userEmailRepository.deleteById(emailId);
        } else {
            log.error("Произошел инцидент: [{}] не является владельцем email [{}]", userId, emailId);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_EMAIL));
        }
    }

    @Override
    public PhoneShortResponseDto addNewPhone(PhoneCreateDto phoneCreateDto, Long userId) {
        log.debug(SAVE_IN_REPOSITORY + "[phoneCreateDto={}] для [userId={}]", phoneCreateDto, userId);
        if (existsPhoneByStringPhone(phoneCreateDto.getPhone())) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phoneCreateDto.getPhone());
            throw new IntegrityConstraintException(PARAMETER_PHONE, phoneCreateDto.getPhone() + NOTE_ALREADY_EXIST);
        }
        UserEntity ownerPhone = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с [userId=%d]", userId)));
        UserPhoneEntity userPhoneEntityForSave = userMapper.toEntityPhoneFromCreateDto(phoneCreateDto, ownerPhone);

        UserPhoneEntity savedUserPhoneEntityInRepository = userPhoneRepository.saveAndFlush(userPhoneEntityForSave);

        return userMapper.toShortPhoneDtoFromPhoneEntity(savedUserPhoneEntityInRepository);
    }

    @Override
    public PhoneShortResponseDto updatePhoneById(PhoneUpdateDto phoneUpdateDto, Long userId) {
        log.debug(UPDATE_IN_REPOSITORY + "[phoneUpdateDto={}] для [userId={}]", phoneUpdateDto, userId);
        if (existsPhoneByStringPhone(phoneUpdateDto.getPhone())) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phoneUpdateDto.getPhone());
            throw new IntegrityConstraintException(PARAMETER_PHONE, phoneUpdateDto.getPhone() + NOTE_ALREADY_EXIST);
        }

        var phoneId = phoneUpdateDto.getId();
        UserPhoneEntity userPhoneEntityFromRepository = userPhoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Телефон с [phoneId=%d]", phoneId)));

        if (userPhoneEntityFromRepository.getOwner().getId().equals(userId)) {
            userPhoneEntityFromRepository.setPhone(phoneUpdateDto.getPhone());
            UserPhoneEntity updatedPhone = userPhoneRepository.saveAndFlush(userPhoneEntityFromRepository);
            log.debug("В БД произошло обновление номера телефона: [{}]", updatedPhone);
            return userMapper.toShortPhoneDtoFromPhoneEntity(updatedPhone);
        } else {
            log.error("Произошел инцидент: [{}] не является владельцем номера телефона [{}]", userId, phoneId);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_PHONE));
        }
    }

    @Override
    public void deletePhoneById(Long userId, Long phoneId) {
        log.debug(DELETE_IN_REPOSITORY + "[phoneId={}] для [userId={}]", phoneId, userId);
        UserPhoneEntity userPhoneEntityFromRepository = userPhoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Телефон с [phoneId=%d]", phoneId)));

        if (userPhoneEntityFromRepository.getOwner().getId().equals(userId)) {
            userPhoneRepository.deleteById(phoneId);
        } else {
            log.error("Произошел инцидент: [{}] не является владельцем номера телефона [{}]", userId, phoneId);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_PHONE));
        }
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

        checkEmailCreateDto(userCreateDto.getEmails());
        checkPhoneCreateDto(userCreateDto.getPhones());
        log.debug("Проверка успешно завершена, пользователя можно сохранять");
    }

    private void checkEmailCreateDto(Set<EmailCreateDto> emails) {
        if (emails.size() == 1) {
            var email = emails.stream().findFirst()
                    .orElseThrow(() -> new IntegrityConstraintException(PARAMETER_EMAIL, emails + NOTE_ALREADY_EXIST));
            if (existsEmailByStringEmail(email.getEmail())) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, email.getEmail());
                throw new IntegrityConstraintException(PARAMETER_EMAIL, email.getEmail() + NOTE_ALREADY_EXIST);
            }
        } else if (emails.size() > 1) {
            if (existsEmailBySetEmails(emails.stream().map(EmailCreateDto::getEmail).collect(Collectors.toSet()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, emails);
                throw new IntegrityConstraintException(PARAMETER_EMAIL, emails + NOTE_ALREADY_EXIST);
            }
        } else {
            log.error(PARAMETER_BAD_REQUEST + "список [email адресов] оказался пуст");
            throw new IncorrectMadeRequestException(PARAMETER_EMAIL, "не может быть пустым");
        }
    }

    private void checkPhoneCreateDto(Set<PhoneCreateDto> phones) {
        if (phones.size() == 1) {
            var phone = phones.stream().findFirst()
                    .orElseThrow(() -> new IntegrityConstraintException(PARAMETER_PHONE, phones + NOTE_ALREADY_EXIST));
            if (existsPhoneByStringPhone(phone.getPhone())) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phone.getPhone());
                throw new IntegrityConstraintException(PARAMETER_PHONE, phone.getPhone() + NOTE_ALREADY_EXIST);
            }
        } else if (phones.size() > 1) {
            if (existsPhoneBySetPhones(phones.stream().map(PhoneCreateDto::getPhone).collect(Collectors.toSet()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[список номеров={}]", phones);
                throw new IntegrityConstraintException(PARAMETER_PHONE, phones + NOTE_ALREADY_EXIST);
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

    private List<UserEmailEntity> sortListUserEmailsByLexicographic(List<UserEmailEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserEmailEntity::getEmail).reversed()).collect(Collectors.toList());
    }

    private List<UserPhoneEntity> sortListUserPhonesByLexicographic(List<UserPhoneEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserPhoneEntity::getPhone).reversed()).collect(Collectors.toList());
    }

    private List<UserEmailEntity> sortListUserEmailsId(List<UserEmailEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserEmailEntity::getId).reversed()).collect(Collectors.toList());
    }

    private List<UserPhoneEntity> sortListUserPhonesId(List<UserPhoneEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserPhoneEntity::getId).reversed()).collect(Collectors.toList());
    }
}
