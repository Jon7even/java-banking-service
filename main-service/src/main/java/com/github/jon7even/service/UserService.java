package com.github.jon7even.service;

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

import java.util.List;

/**
 * Интерфейс для взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserService {
    /**
     * Метод регистрирующий нового пользователя в системе
     *
     * @param userCreateDto заполненный объект DTO с данными пользователя
     * @return UserFullResponseDto объект DTO с подробными данными о пользователе
     */
    UserFullResponseDto createUser(UserCreateDto userCreateDto);

    /**
     * Метод поиска пользователей в системе
     *
     * @param paramsSearchUserRequestDto объект DTO с параметрами поиска
     * @return UserShortResponseDto список объектов DTO с краткими данными о пользователе
     */
    List<UserShortResponseDto> getListUsersByParam(ParamsSearchUserRequestDto paramsSearchUserRequestDto);

    /**
     * Метод добавляющий новый номер телефона в профиль пользователя
     *
     * @param phoneCreateDto заполненный объект DTO с новым phone
     * @return EmailShortResponseDto объект DTO с добавленным phone
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
