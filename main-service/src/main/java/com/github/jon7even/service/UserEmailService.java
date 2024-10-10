package com.github.jon7even.service;

import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.email.EmailUpdateDto;
import com.github.jon7even.entity.UserEntity;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс для взаимодействия с электронной почтой пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserEmailService {
    /**
     * Метод для создания списка emails для профиля нового пользователя
     *
     * @param emailsCreateDto список заполненных объектов DTO с новыми электронными адресами, минимум 1 запись
     * @param newUserEntity   пользователь, который будет являться владельцем электронных адресов
     * @return EmailShortResponseDto список объектов DTO
     */
    List<EmailShortResponseDto> createNewEmails(Set<EmailCreateDto> emailsCreateDto, UserEntity newUserEntity);

    /**
     * Метод добавляющий новую электронную почту в профиль пользователя
     *
     * @param emailCreateDto заполненный объект DTO с новым email
     * @return EmailShortResponseDto объект DTO с добавленным email
     */
    EmailShortResponseDto addNewEmail(EmailCreateDto emailCreateDto, Long userId);

    /**
     * Метод обновляющий существующую почту в профиле пользователя
     *
     * @param emailUpdateDto заполненный объект DTO с email
     * @param userId         ID пользователя
     * @return EmailShortResponseDto объект DTO с обновленным email
     */
    EmailShortResponseDto updateEmailById(EmailUpdateDto emailUpdateDto, Long userId);

    /**
     * Метод для удаления электронного адреса из профиля пользователя
     *
     * @param userId  ID пользователя
     * @param emailId ID почты, которую требуется удалить
     */
    void deleteEmailById(Long userId, Long emailId);
}
