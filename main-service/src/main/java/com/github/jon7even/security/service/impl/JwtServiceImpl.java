package com.github.jon7even.security.service.impl;

import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.security.configuration.TokenConfig;
import com.github.jon7even.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Реализация сервиса работы с JWT-токеном
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final TokenConfig tokenConfig;

    @Override
    public String extractUserName(String token) {
        log.debug("Пришел запрос на извлечения логина из токена: [{}]", token);
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        log.debug("Пришел запрос на генерацию нового токена: [{}]", userDetails);
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("firstName", customUserDetails.getFirstName());
            claims.put("lastName", customUserDetails.getLastName());
            claims.put("middleName", customUserDetails.getMiddleName());
        }
        return generateToken(claims, userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.debug("Пришел запрос на проверку валидности токена: [{}] от пользователя: [{}]", userDetails, userDetails);
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Метод для извлечения данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return <T> данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Метод для извлечения всех данных из токена
     *
     * @param token токен
     * @return Claims данные
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Метод для получения ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenConfig.getKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Основной метод генерации токена
     *
     * @param extraClaims дополнительные данные, которые помогут увеличить уникальность
     * @param userDetails данные пользователя
     * @return String токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.debug("Генерируем токен для: [{}]", userDetails);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenConfig.getLifetime()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Метод для проверки токена на просроченность
     *
     * @param token токен
     * @return boolean true если просрочен
     */
    private boolean isTokenExpired(String token) {
        log.debug("Проверяем токен на просроченность: [{}]", token);
        return extractExpiration(token).before(new Date());
    }

    /**
     * Метод для извлечения даты истечения токена
     *
     * @param token токен
     * @return Date дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
