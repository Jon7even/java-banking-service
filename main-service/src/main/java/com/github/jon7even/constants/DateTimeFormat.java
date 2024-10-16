package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для работы с парсингом времени
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeFormat {
    public static final String DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_DEFAULT = "dd-MM-yyyy";
}
