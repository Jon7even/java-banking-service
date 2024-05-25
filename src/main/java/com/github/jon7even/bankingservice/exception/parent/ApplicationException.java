package com.github.jon7even.bankingservice.exception.parent;

import com.github.jon7even.bankingservice.exception.model.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Подкласс-шаблон для наследуемых исключений
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используйте этот шаблон для возникающих исключений в процессе выполнения приложения. Данная стандартизация
 * позволяет избегать дублирования кода и возможных исключений и автоматически будет использована в обработчике ошибок
 * в контроллерах (ErrorHandler)
 */
@Getter
public class ApplicationException extends RuntimeException {
    private final ApiError responseException;
    private final HttpStatus httpStatus;

    public ApplicationException(String status, String reason, String message, HttpStatus httpStatus) {
        this.responseException = ApiError.builder()
                .status(status)
                .reason(reason)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        this.httpStatus = httpStatus;
    }
}
