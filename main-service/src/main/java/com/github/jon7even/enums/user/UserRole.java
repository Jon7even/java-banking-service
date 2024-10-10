package com.github.jon7even.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление возможных ролей для пользователей в приложении
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote по ТЗ ролей не требуется, поэтому пока здесь стоит только заглушка
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    NO_ROLE("NONE");

    private final String role;
}
