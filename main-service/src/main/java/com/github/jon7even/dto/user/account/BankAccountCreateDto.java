package com.github.jon7even.dto.user.account;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для открытия счета
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Должен содержать изначальную сумму
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreateDto {
    @PositiveOrZero(message = "Поле [balance] должно быть положительным или равным 0")
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}
