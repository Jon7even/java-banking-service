package com.github.jon7even.dto.user.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс DTO для обновления существующей электронной почты пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailUpdateDto {
    @NotNull(message = "Поле [id] не может быть пустым")
    @Positive(message = "Поле [id] должно быть положительным")
    private Long id;

    @NotBlank(message = "Поле [email] не может быть пустым")
    private String email;
}
