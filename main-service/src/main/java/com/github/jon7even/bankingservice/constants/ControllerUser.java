package com.github.jon7even.bankingservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения адресов к эндпоинту "Пользователи"(users)
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerUser {
    public static final String PATH_USERS = "/users";
    public static final String PATH_SEARCH = "/search";
    public static final String PATH_USER_ID = "/{userId}";
}
