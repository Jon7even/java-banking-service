package com.github.jon7even.service.impl;

import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneUpdateDto;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.entity.UserPhoneEntity;
import com.github.jon7even.exception.AccessDeniedException;
import com.github.jon7even.exception.EntityNotFoundException;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.exception.IntegrityConstraintException;
import com.github.jon7even.mapper.UserPhoneMapper;
import com.github.jon7even.repository.UserPhoneRepository;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.UserPhoneService;
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
 * Реализация сервиса взаимодействия с номерами телефонов пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserPhoneServiceImpl implements UserPhoneService {
    private final UserPhoneRepository userPhoneRepository;
    private final UserPhoneMapper userPhoneMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = REPEATABLE_READ)
    public List<PhoneShortResponseDto> createNewPhones(Set<PhoneCreateDto> phonesCreateDto, UserEntity newUserEntity) {
        log.trace(SAVE_IN_REPOSITORY + "[phonesCreateDto={}]", phonesCreateDto);
        checkListPhonesCreateDto(phonesCreateDto);

        List<UserPhoneEntity> userPhoneEntitiesForSaveInRepository =
                userPhoneMapper.toListEntityPhoneFromCreateDto(phonesCreateDto.stream().toList());
        userPhoneEntitiesForSaveInRepository.forEach(userPhone -> userPhone.setOwner(newUserEntity));

        List<UserPhoneEntity> savedPhones = userPhoneRepository.saveAllAndFlush(
                sortListUserPhonesByLexicographic(userPhoneEntitiesForSaveInRepository)
        );
        log.trace("Успешно сохранен новый список номеров телефона нового пользователя: [phones={}]", savedPhones);
        return userPhoneMapper.toShortListPhoneDtoFromPhoneEntity(sortListUserPhonesId(savedPhones));
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public PhoneShortResponseDto addNewPhone(PhoneCreateDto phoneCreateDto, Long userId) {
        log.trace(SAVE_IN_REPOSITORY + "[phoneCreateDto={}] для [userId={}]", phoneCreateDto, userId);
        checkPhoneInRepositoryByStringPhone(phoneCreateDto.getPhone());

        UserEntity ownerPhone = findUserEntityById(userId);
        UserPhoneEntity userPhoneEntityForSave = userPhoneMapper.toEntityPhoneFromCreateDto(phoneCreateDto, ownerPhone);

        UserPhoneEntity savedUserPhoneEntityInRepository = userPhoneRepository.saveAndFlush(userPhoneEntityForSave);
        log.trace("В БД добавлен новый [{}]: [{}]", PARAMETER_PHONE, userPhoneEntityForSave);
        return userPhoneMapper.toShortPhoneDtoFromPhoneEntity(savedUserPhoneEntityInRepository);
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public PhoneShortResponseDto updatePhoneById(PhoneUpdateDto phoneUpdateDto, Long userId) {
        log.trace(UPDATE_IN_REPOSITORY + "[phoneUpdateDto={}] для [userId={}]", phoneUpdateDto, userId);
        checkPhoneInRepositoryByStringPhone(phoneUpdateDto.getPhone());

        UserPhoneEntity userPhoneEntityFromRepository = findUserPhoneEntityById(phoneUpdateDto.getId());
        checkUserIsOwnerPhone(userPhoneEntityFromRepository, userId);
        userPhoneEntityFromRepository.setPhone(phoneUpdateDto.getPhone());

        UserPhoneEntity updatedPhone = userPhoneRepository.saveAndFlush(userPhoneEntityFromRepository);
        log.trace("В БД произошло обновление [{}]: [{}]", PARAMETER_PHONE, updatedPhone);
        return userPhoneMapper.toShortPhoneDtoFromPhoneEntity(updatedPhone);
    }

    @Override
    @Transactional(isolation = REPEATABLE_READ)
    public void deletePhoneById(Long userId, Long phoneId) {
        log.trace(DELETE_IN_REPOSITORY + "[phoneId={}] для [userId={}]", phoneId, userId);
        checkSizeListOfPhoneUserByUserId(userId);
        UserPhoneEntity userPhoneEntityFromRepository = findUserPhoneEntityById(phoneId);
        checkUserIsOwnerPhone(userPhoneEntityFromRepository, userId);
        userPhoneRepository.deleteById(phoneId);
    }

    private void checkListPhonesCreateDto(Set<PhoneCreateDto> phones) {
        log.trace("{} список объектов DTO с [phone] перед сохранением в БД", START_CHECKING);
        if (phones.size() == 1) {
            var phone = phones.stream().findFirst()
                    .orElseThrow(() -> new IncorrectMadeRequestException(PARAMETER_PHONE, phones + NOT_EXIST));
            checkPhoneInRepositoryByStringPhone(phone.getPhone());
        } else if (phones.size() > 1) {
            if (existsPhoneBySetPhones(phones.stream().map(PhoneCreateDto::getPhone).collect(Collectors.toSet()))) {
                log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phones);
                throw new IntegrityConstraintException(PARAMETER_PHONE, phones + NOTE_ALREADY_EXIST);
            }
        } else {
            log.error(PARAMETER_BAD_REQUEST + "список [номеров] оказался пуст");
            throw new IncorrectMadeRequestException(PARAMETER_PHONE, "не может быть пустым");
        }
        log.debug("{} список [номеров]", END_CHECKING);
    }

    private void checkUserIsOwnerPhone(UserPhoneEntity userPhone, Long userId) {
        log.debug("{} является ли пользователь с [userId={}] владельцем [phone={}]", START_CHECKING, userId, userPhone);
        if (!userPhone.getOwner().getId().equals(userId)) {
            log.error("Произошел инцидент: [userId={}] не является владельцем [phone={}]", userId, userPhone);
            throw new AccessDeniedException(String.format("Вы не являетесь владельцем [%s]", PARAMETER_PHONE));
        }
    }

    private void checkPhoneInRepositoryByStringPhone(String phone) {
        log.trace("{} объект DTO с [{}] перед сохранением в БД", START_CHECKING, PARAMETER_PHONE);
        if (existsPhoneByStringPhone(phone)) {
            log.warn(PARAMETER_ALREADY_EXIST_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phone);
            throw new IntegrityConstraintException(PARAMETER_PHONE, phone + NOTE_ALREADY_EXIST);
        }
        log.debug("{} [{}]", END_CHECKING, PARAMETER_PHONE);
    }

    private List<UserPhoneEntity> sortListUserPhonesByLexicographic(List<UserPhoneEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserPhoneEntity::getPhone).reversed()).collect(Collectors.toList());
    }

    private List<UserPhoneEntity> sortListUserPhonesId(List<UserPhoneEntity> listNoSorted) {
        return listNoSorted.stream()
                .sorted(Comparator.comparing(UserPhoneEntity::getId).reversed()).collect(Collectors.toList());
    }

    private boolean existsPhoneByStringPhone(String phone) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[{}={}]", PARAMETER_PHONE, phone);
        return userPhoneRepository.existsByPhone(phone);
    }

    private boolean existsPhoneBySetPhones(Set<String> phones) {
        log.debug(CHECK_PARAMETER_IN_REPOSITORY + "[список номеров телефонов={}]", phones);
        var listOfPhones = userPhoneRepository.getPhoneEntityBySetPhones(phones);
        return !listOfPhones.isEmpty();
    }

    private void checkSizeListOfPhoneUserByUserId(Long userId) {
        log.debug("{} количество записей phone по [userId={}]", START_CHECKING, userId);
        if (getListOfPhonesByOwnerId(userId).size() < 2) {
            log.warn("Пользователь с [userId={}] пытается удалить свою единственную {}", userId, PARAMETER_PHONE);
            throw new IntegrityConstraintException(PARAMETER_PHONE, MIN_SIZE_LIST);
        }
    }

    List<UserPhoneEntity> getListOfPhonesByOwnerId(Long userId) {
        log.debug("{}list phones by [userId={}]", SEARCH_IN_REPOSITORY, userId);
        return userPhoneRepository.findByOwnerId(userId);
    }

    private UserPhoneEntity findUserPhoneEntityById(Long phoneId) {
        log.debug("{}[phoneId={}]", SEARCH_IN_REPOSITORY, phoneId);
        return userPhoneRepository.findById(phoneId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Phone с [phoneId=%d]", phoneId)));
    }

    private UserEntity findUserEntityById(Long userId) {
        log.debug("{}[userId={}]", SEARCH_IN_REPOSITORY, userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с [userId=%d]", userId)));
    }
}
