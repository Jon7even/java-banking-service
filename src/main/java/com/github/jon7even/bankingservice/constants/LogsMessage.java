package com.github.jon7even.bankingservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранения часто-используемых слов для работы с логами сервера
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogsMessage {
    public static final String IN_CONTROLLER_METHOD = "был использован метод";
    public static final String SAVE_IN_REPOSITORY = "Начинаем сохранять новую сущность, данные для сохранения: ";
    public static final String CHECK_PARAMETER_IN_REPOSITORY = "Проверяем существует ли такой параметр в БД: ";
    public static final String PARAMETER_ALREADY_EXIST_IN_REPOSITORY = "Сущность с таким параметром уже существует: ";
}
