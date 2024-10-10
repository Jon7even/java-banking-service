package com.github.jon7even.security.service.impl;

import com.github.jon7even.dto.user.security.JwtAuthResponseDto;
import com.github.jon7even.dto.user.security.SignInRequestDto;
import com.github.jon7even.security.service.AuthenticationService;
import com.github.jon7even.security.service.JwtService;
import com.github.jon7even.security.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса Аутентификации
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserSecurityService userSecurityService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponseDto signIn(SignInRequestDto signInRequestDto) {
        log.debug("К нам на авторизацию пришел [{}]", signInRequestDto);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDto.getLogin(),
                signInRequestDto.getPassword()
        ));
        var userForAuthentication = userSecurityService.userDetailsService()
                .loadUserByUsername(signInRequestDto.getLogin());
        var generatedTokenForUser = jwtService.generateToken(userForAuthentication);
        log.debug("Пользователь [{}] прошел авторизацию", signInRequestDto);
        return JwtAuthResponseDto.builder().token(generatedTokenForUser).build();
    }
}
