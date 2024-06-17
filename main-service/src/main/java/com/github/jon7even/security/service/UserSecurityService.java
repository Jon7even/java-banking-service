package com.github.jon7even.security.service;

import com.github.jon7even.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Интерфейс сервиса для работы с пользователями
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используется для работы с компонентами Spring Security
 */
public interface UserSecurityService {
    /**
     * Метод получения пользователя по логину из репозитория
     *
     * @param username логин пользователя
     * @return User
     */
    UserEntity getByUsername(String username);

    /**
     * Метод получения пользователя по логину
     *
     * @return UserDetailsService
     */
    UserDetailsService userDetailsService();

    /**
     * Метод получения текущего пользователя из контекста Spring Security
     *
     * @return User
     */
    UserEntity getCurrentUser();
}
