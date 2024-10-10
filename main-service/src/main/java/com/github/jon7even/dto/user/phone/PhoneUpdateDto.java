package com.github.jon7even.dto.user.phone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для обновления существующего номера телефона пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneUpdateDto {
    @NotNull(message = "Поле [id] не может быть пустым")
    @Positive(message = "Поле [id] должно быть положительным")
    private Long id;

    @NotBlank(message = "Поле [phone] не может быть пустым")
    private String phone;
}

