package com.github.jon7even.bankingservice.exception.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_TIME_DEFAULT;

/**
 * Класс для представления ошибок в виде модели
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@RequiredArgsConstructor
public class ApiError {
    private final List<String> errors;

    private final String status;

    private final String reason;

    private final String message;

    @JsonFormat(pattern = DATE_TIME_DEFAULT)
    private final LocalDateTime timestamp;
}
