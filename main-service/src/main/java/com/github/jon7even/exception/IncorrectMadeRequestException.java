package com.github.jon7even.exception;

import com.github.jon7even.exception.parent.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * Класс описывающий исключение если пользователь сделал неправильный запрос
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Самый распространенный случай это валидация полей и неправильное значение этих полей (например количество)
 */
public class IncorrectMadeRequestException extends ApplicationException {
    public IncorrectMadeRequestException(String checkedValue, String incomingValue) {
        super("CONFLICT", "Integrity constraint has been violated.",
                String.format("При проверке поля [%s] произошла ошибка, пришло нерелевантное значение: [%s]",
                        checkedValue, incomingValue), HttpStatus.CONFLICT);
    }
}
