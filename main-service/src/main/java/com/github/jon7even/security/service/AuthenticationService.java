package com.github.jon7even.security.service;

import com.github.jon7even.dto.user.security.JwtAuthResponseDto;
import com.github.jon7even.dto.user.security.SignInRequestDto;

/**
 * Интерфейс сервиса для аутентификации пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
public interface AuthenticationService {
    /**
     * Метод авторизации пользователя
     *
     * @param signInRequestDto заполненный объект DTO с данными пользователя
     * @return JwtAuthResponseDto с полученным токеном
     */
    JwtAuthResponseDto signIn(SignInRequestDto signInRequestDto);
}
