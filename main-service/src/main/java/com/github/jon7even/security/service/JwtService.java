package com.github.jon7even.security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Интерфейс сервиса для работы с JWT-токен
 *
 * @author Jon7even
 * @version 1.0
 */
public interface JwtService {
    /**
     * Метод извлечения логина пользователя из токена
     *
     * @param token токен
     * @return String получившийся логин
     */
    String extractUserName(String token);

    /**
     * Метод генерации токена
     *
     * @param userDetails данные пользователя
     * @return String получившийся токен
     */
    String generateToken(UserDetails userDetails);

    /**
     * Метод проверки токена на валидность
     *
     * @param token       токен
     * @param userDetails данные пользователя
     * @return boolean true если валиден
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
