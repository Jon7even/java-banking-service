package com.github.jon7even.exception;

import com.github.jon7even.exception.parent.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * Класс описывающий исключение если сущность не найдена в репозитории
 *
 * @author Jon7even
 * @version 1.0
 */
public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String message) {
        super("NOT_FOUND", "The required object was not found.",
                String.format("%s не был найден", message),
                HttpStatus.NOT_FOUND);
    }
}

