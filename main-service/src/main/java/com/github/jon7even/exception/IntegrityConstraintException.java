package com.github.jon7even.exception;

import com.github.jon7even.exception.parent.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * Класс описывающий исключение если в БД нарушается целостность
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Самый распространенный случай это когда в БД стоит ограничение на уникальность какого-либо поля и при
 * попытке добавить новый объект с полем, которое должно быть уникально, СУБД выдаст ошибку, которую перехватит наше
 * приложение и выдаст это исключение
 */
public class IntegrityConstraintException extends ApplicationException {
    public IntegrityConstraintException(String checkedValue, String incomingValue) {
        super("CONFLICT", "Integrity constraint has been violated.",
                String.format("При проверке поля [%s] произошла ошибка, пришло нерелевантное значение: [%s]",
                        checkedValue, incomingValue), HttpStatus.CONFLICT);
    }
}