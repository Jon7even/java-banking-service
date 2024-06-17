package com.github.jon7even.dto.user.transfer;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для создания денежного перевода между пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferCreateDto {
    @Positive(message = "Поле [toBankAccountId] должно быть положительным")
    private Long toBankAccountId;

    @Positive(message = "Поле [amount] должно быть больше 0")
    private BigDecimal amount;
}
