package com.github.jon7even.dto.user.phone;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для добавления нового телефона пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCreateDto {
    @NotBlank(message = "Поле [phone] не может быть пустым")
    private String phone;
}
