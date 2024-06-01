package com.github.jon7even.security.service.impl;

import com.github.jon7even.exception.EntityNotFoundException;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.security.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса работы с пользователями для Spring Security
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;

    @Override
    public User getByUsername(String login) {
        log.debug("Пришел запрос на получение пользователя по [login={}] из репозитория", login);
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с [login=%s]", login)));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public User getCurrentUser() {
        log.debug("Пришел запрос на получение логина пользователя из контекста Spring Security");
        var login = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(login);
    }
}
