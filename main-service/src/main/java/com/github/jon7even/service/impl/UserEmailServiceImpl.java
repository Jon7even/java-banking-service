package com.github.jon7even.service.impl;

import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.email.EmailUpdateDto;
import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.EntityNotFoundException;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.mapper.UserEmailMapper;
import com.github.jon7even.repository.UserEmailRepository;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.jon7even.constants.LogsMessage.*;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

/**
 * Реализация сервиса взаимодействия с электронной почтой пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserEmailServiceImpl implements UserEmailService {
    private final UserEmailRepository userEmailRepository;
    private final UserEmailMapper userEmailMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = REPEATABLE_READ)
    public List<EmailShortResponseDto> createNewEmails(Set<EmailCreateDto> emailsCreateDto, UserEntity newUserEntity) {
        log.trace(SAVE_IN_REPOSITORY + "[emailsCreateDto={}]", emailsCreateDto);
        checkListEmailsCreateDto(emailsCreateDto);

        List<UserEmailEntity> userEmailEntitiesForSaveInRepository =
                userEmailMapper.toListEntityEmailFromCreateDto(emailsCreateDto.stream().toList());
        userEmailEntitiesForSaveInRepository.forEach(userEmail -> userEmail.setOwner(newUserEntity));

        List<UserEmailEntity> savedEmails = userEmailRepository.saveAllAndFlush(
                sortListUserEmailsByLexicographic(userEmailEntitiesForSaveInRepository)
        );
        log.trace("Успешно сохранен новый список электронных адресов нового пользователя: [emails={}]", savedEmails);
        return userEmailMapper.toShortListEmailDtoFromEmailEntity(sortListUserEmailsId(savedEmails));
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public EmailShortResponseDto addNewEmail(EmailCreateDto emailCreateDto, Long userId) {
        log.trace(SAVE_IN_REPOSITORY + "[emailCreateDto={}] для [userId={}]", emailCreateDto, userId);
        checkEmailInRepositoryByStringEmail(emailCreateDto.getEmail());

        UserEntity ownerEmail = findUserEntityById(userId);
        UserEmailEntity userEmailEntityForSave = userEmailMapper.toEntityEmailFromCreateDto(emailCreateDto, ownerEmail);

        UserEmailEntity savedUserEmailEntityInRepository = userEmailRepository.saveAndFlush(userEmailEntityForSave);
        log.trace("В БД добавлен новый [{}]: [{}]", PARAMETER_EMAIL, userEmailEntityForSave);
        return userEmailMapper.toShortEmailDtoFromEmailEntity(savedUserEmailEntityInRepository);
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public EmailShortResponseDto updateEmailById(EmailUpdateDto emailUpdateDto, Long userId) {
        log.trace(UPDATE_IN_REPOSITORY + "[emailUpdateDto={}] для [userId={}]", emailUpdateDto, userId);
        checkEmailInRepositoryByStringEmail(emailUpdateDto.getEmail());

        UserEmailEntity userEmailEntityFromRepository = findUserEmailEntityById(emailUpdateDto.getId());
        checkUserIsOwnerEmail(userEmailEntityFromRepository, userId);
        userEmailEntityFromRepository.setEmail(emailUpdateDto.getEmail());

        UserEmailEntity updatedEmail = userEmailRepository.saveAndFlush(userEmailEntityFromRepository);
        log.trace("В БД произошло обновление [{}]: [{}]", PARAMETER_EMAIL, updatedEmail);
        return userEmailMapper.toShortEmailDtoFromEmailEntity(updatedEmail);
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public void deleteEmailById(Long userId, Long emailId) {
        log.trace(DELETE_IN_REPOSITORY + "[emailId={}] для [userId={}]", emailId, userId);
        checkSizeListOfEmailUserByUserId(userId);
        UserEmailEntity userEmailEntityFromRepository = findUserEmailEntityById(emailId);
        checkUserIsOwnerEmail(userEmailEntityFromRepository, userId);
        userEmailRepository.deleteById(emailId);
    }

    private void checkListEmailsCreateDto(Set<EmailCreateDto> emails) {
        log.trace("{} список объектов DTO с [email] перед сохранением в БД", START_CHECKING);
        if (emails.size() == 1) {
            var email = emails.stream().findFirst()
                    .orElseThrow(() -> new IncorrectMadeRequestException(PARAMETER_EMAIL, emails + NOT_EXIST));
            checkEmailInRepositoryByStringEmail(email.getEmail());
        } else if (emails.size() > 1) {
            if (existsEmailBySetEmails(emails.stream().map(EmailCreateDto::getEmail).collect(Collectors.toSet()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, emails);
                throw new IntegrityConstraintException(PARAMETER_EMAIL, emails + NOTE_ALREADY_EXIST);
            }
        } else {
            log.error(PARAMETER_BAD_REQUEST + "список [email адресов] оказался пуст");
            throw new IncorrectMadeRequestException(PARAMETER_EMAIL, "не может быть пустым");
        }
        log.debug("{} список [email адресов]", END_CHECKING);
    }

    private List<UserEmailEntity> sortListUserEmailsByLexicographic(List<UserEmailEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserEmailEntity::getEmail).reversed()).collect(Collectors.toList());
    }

    private List<UserEmailEntity> sortListUserEmailsId(List<UserEmailEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserEmailEntity::getId).reversed()).collect(Collectors.toList());
    }

    private void checkEmailInRepositoryByStringEmail(String email) {
        log.trace("{} объект DTO с [{}] перед сохранением в БД", START_CHECKING, PARAMETER_EMAIL);
        if (existsEmailByStringEmail(email)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, email);
            throw new IntegrityConstraintException(PARAMETER_EMAIL, email + NOTE_ALREADY_EXIST);
        }
        log.debug("{} [{}]", END_CHECKING, PARAMETER_EMAIL);
    }

    private void checkUserIsOwnerEmail(UserEmailEntity userEmail, Long userId) {
        log.debug("{} является ли пользователь с [userId={}] владельцем [email={}]", START_CHECKING, userId, userEmail);
        if (!userEmail.getOwner().getId().equals(userId)) {
            log.error("Произошел инцидент: [userId={}] не является владельцем [email={}]", userId, userEmail);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_EMAIL));
        }
    }

    private boolean existsEmailByStringEmail(String email) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_EMAIL, email);
        return userEmailRepository.existsByEmail(email);
    }

    private boolean existsEmailBySetEmails(Set<String> emails) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[список адресов={}]", emails);
        var listOfEmails = userEmailRepository.getEmailEntityBySetEmails(emails);
        return !listOfEmails.isEmpty();
    }

    private void checkSizeListOfEmailUserByUserId(Long userId) {
        log.debug("{} количество записей email по [userId={}]", START_CHECKING, userId);
        if (getListOfEmailsByOwnerId(userId).size() < 2) {
            log.warn("Пользователь с [userId={}] пытается удалить свою единственную {}", userId, PARAMETER_EMAIL);
            throw new IntegrityConstraintException(PARAMETER_EMAIL, MIN_SIZE_LIST);
        }
    }

    List<UserEmailEntity> getListOfEmailsByOwnerId(Long userId) {
        log.debug("{}list emails by [userId={}]", SEARCH_IN_REPOSITORY, userId);
        return userEmailRepository.findByOwnerId(userId);
    }

    private UserEmailEntity findUserEmailEntityById(Long emailId) {
        log.debug("{}[emailId={}]", SEARCH_IN_REPOSITORY, emailId);
        return userEmailRepository.findById(emailId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Email с [emailId=%d]", emailId)));
    }

    private UserEntity findUserEntityById(Long userId) {
        log.debug("{}[userId={}]", SEARCH_IN_REPOSITORY, userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с [userId=%d]", userId)));
    }
}
