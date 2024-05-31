package com.github.jon7even.controller.handler;

import com.github.jon7even.exception.model.ApiError;
import com.github.jon7even.exception.parent.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Общий класс для обработки собственных ошибок и  в контроллерах
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Распространяется на все подключенные контроллеры
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(ApplicationException exception) {
        HttpStatus responseStatus = exception.getHttpStatus();
        String message = exception.getMessage();

        if (responseStatus.is4xxClientError()) {
            log.warn(message);
        } else if (responseStatus.is5xxServerError()) {
            log.error(message);
        } else {
            log.debug(message);
        }

        return ResponseEntity.status(exception.getHttpStatus())
                .body(exception.getResponseException());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());

        ApiError responseException = ApiError.builder()
                .status("BAD_REQUEST")
                .reason("Incorrectly made request.")
                .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseException);
    }
}
