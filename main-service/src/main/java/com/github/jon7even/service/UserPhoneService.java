package com.github.jon7even.service;

import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneUpdateDto;
import com.github.jon7even.entity.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для взаимодействия с номерами телефонов пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserPhoneService {
    /**
     * Метод для создания списка phones для профиля нового пользователя
     *
     * @param phonesCreateDto список заполненных объектов DTO с новыми телефонными номерами, минимум 1 запись
     * @param newUserEntity   пользователь, который будет являться владельцем телефонных номеров
     * @return PhoneShortResponseDto список объектов DTO
     */
    List<PhoneShortResponseDto> createNewPhones(Set<PhoneCreateDto> phonesCreateDto, UserEntity newUserEntity);

    /**
     * Метод добавляющий новый номер телефона в профиль пользователя
     *
     * @param phoneCreateDto заполненный объект DTO с новым phone
     * @return PhoneShortResponseDto объект DTO с добавленным phone
     */
    PhoneShortResponseDto addNewPhone(PhoneCreateDto phoneCreateDto, Long userId);

    /**
     * Метод обновляющий существующий номер телефона в профиле пользователя
     *
     * @param phoneUpdateDto заполненный объект DTO с phone
     * @param userId         ID пользователя
     * @return PhoneShortResponseDto объект DTO с обновленным phone
     */
    PhoneShortResponseDto updatePhoneById(PhoneUpdateDto phoneUpdateDto, Long userId);

    /**
     * Метод для удаления номера телефона из профиля пользователя
     *
     * @param userId  ID пользователя
     * @param phoneId ID номера телефона, который требуется удалить
     */
    void deletePhoneById(Long userId, Long phoneId);
}
