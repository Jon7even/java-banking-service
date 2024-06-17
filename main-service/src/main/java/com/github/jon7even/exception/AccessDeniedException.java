package com.github.jon7even.exception;

import com.github.jon7even.exception.parent.ApplicationException;
import org.springframework.http.HttpStatus;

/**
 * Класс описывающий исключение если доступ к ресурсу запрещен
 *
 * @author Jon7even
 * @version 1.0
 */
public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException(String message) {
        super("FORBIDDEN", "You don't permissions for it operation.",
                String.format("%s", message),
                HttpStatus.FORBIDDEN);
    }
}

