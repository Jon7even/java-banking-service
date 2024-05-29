package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения возможных общих путей к эндпоинтам
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerApi {
    public static final String PATH_API = "/api/v1";
    public static final String PATH_SYSTEM = "/system";
}
