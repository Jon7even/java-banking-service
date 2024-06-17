package com.github.jon7even.dto.user.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для краткого представления информации о текущем балансе пользователе
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountShortResponseDto {
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}
