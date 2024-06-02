package com.github.jon7even.constants;

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
    public static final String PARAMETER_BAD_REQUEST = "Пользователь сделал недопустимый запрос: ";
    public static final String PARAMETER_EMAIL = "адрес электронной почты";
    public static final String PARAMETER_LOGIN = "логин";
    public static final String PARAMETER_PHONE = "номер телефона";
    public static final String PARAMETER_BALANCE = "счет";
    public static final String PARAMETER_DATE_OF_BIRTH = "день рождения";
    public static final String PARAMETER_FULL_NAME = "ФИО";
    public static final String SORT_REQUIRED = "Сортировка определена по:";
    public static final String SORT_NOT_REQUIRED = "Сортировка не требуется, поиск будет по:";
    public static final String SEARCH_BY = "Поиск будет выполнен по:";
    public static final String WRONG_CAN_NOT = " не может быть ";
    public static final String NOTE_ALREADY_EXIST = " уже существует";
    public static final String ACCESS_DENIED = " доступ запрещен";
}
