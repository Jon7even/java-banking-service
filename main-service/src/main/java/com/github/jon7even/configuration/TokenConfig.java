package com.github.jon7even.configuration;

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
@ConfigurationProperties("token")
public class TokenConfig {
    private String key;
    private int lifeTime;
}
