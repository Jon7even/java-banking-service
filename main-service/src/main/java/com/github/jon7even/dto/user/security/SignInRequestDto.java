package com.github.jon7even.dto.user.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Класс DTO для авторизации пользователей в системе через JWT-токен
 *
 * @author Jon7even
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {
    @NotBlank(message = "Поле [login] не может быть пуcтым")
    private String login;

    @NotBlank(message = "Поле [password] не может быть пуcтым")
    @ToString.Exclude
    private String password;
}
