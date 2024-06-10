package com.github.jon7even.dto.transaction;

import com.github.jon7even.enums.transaction.TransactionStatus;
import com.github.jon7even.enums.transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для сохранения новой транзакции в БД
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateDto {
    @Positive(message = "Поле [accountId] должно быть положительным")
    private Long accountId;

    @NotBlank(message = "Поле [from] не может быть пустым")
    private String from;

    @Positive(message = "Поле [balance] должно быть положительным")
    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;

    @NotNull(message = "Поле [status] не может быть пустым")
    private TransactionStatus status;

    @NotNull(message = "Поле [type] не может быть пустым")
    private TransactionType type;
}