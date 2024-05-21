package com.github.jon7even.bankingservice.service;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;

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
     * @param userCreateDto заполненный объект DTO
     * @return UserFullResponseDto объект DTO с подробными данными о пользователе
     */
    UserFullResponseDto createUser(UserCreateDto userCreateDto);
}
