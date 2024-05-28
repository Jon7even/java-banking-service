package com.github.jon7even.bankingservice.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для открытия счета, содержит изначальную сумму
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountCreateDto {
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}
