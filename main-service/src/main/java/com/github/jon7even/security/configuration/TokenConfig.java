package com.github.jon7even.security.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Утилитарный класс загружающий токен и его время жизни
 *
 * @author Jon7even
 * @version 1.0
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("spring.token")
public class TokenConfig {
    private String key;
    private Integer lifetime;
}
