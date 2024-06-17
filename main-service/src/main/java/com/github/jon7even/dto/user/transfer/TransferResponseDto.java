package com.github.jon7even.dto.user.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс DTO для предоставления информации о денежном переводе между пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    private BigDecimal currentBalance;
    private BigDecimal amountDebited;
    private String status;
}
