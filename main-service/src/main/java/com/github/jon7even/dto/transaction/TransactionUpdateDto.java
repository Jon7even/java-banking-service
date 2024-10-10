package com.github.jon7even.dto.transaction;

import com.github.jon7even.enums.transaction.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для обновления статуса существующей транзакции
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpdateDto {
    @Positive(message = "Поле [transactionId] должно быть положительным")
    private Long transactionId;

    @NotNull(message = "Поле [status] не может быть пустым")
    private TransactionStatus status;
}
