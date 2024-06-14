package com.github.jon7even.constants;

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
    public static final String PATH_USER_ID = "/{userId}";
    public static final String PATH_SEARCH = "/search";
    public static final String PATH_EMAIL = "/email";
    public static final String PATH_EMAIL_ID = "/{emailId}";
    public static final String PATH_PHONE = "/phone";
    public static final String PATH_PHONE_ID = "/{phoneId}";
    public static final String PATH_TRANSFER = "/transfer";
}
