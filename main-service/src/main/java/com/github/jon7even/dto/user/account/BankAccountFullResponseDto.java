package com.github.jon7even.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для полного представления информации о банковском аккаунте пользователя
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используется внутренними сервисами для подтверждения транзакций
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountFullResponseDto {
    private Long transactionId;

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}
