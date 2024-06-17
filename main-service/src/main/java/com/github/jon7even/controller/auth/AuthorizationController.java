package com.github.jon7even.controller.auth;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.security.JwtAuthResponseDto;
import com.github.jon7even.dto.user.security.SignInRequestDto;
import com.github.jon7even.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.jon7even.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Контроллер для авторизации зарегистрированных пользователей
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Не предполагает регистрацию, поскольку пользователи регистрируется на служебном API
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ControllerApi.PATH_AUTH)
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Авторизация пользователя",
            description = "Чтобы войти в систему нужно ввести существующий логин и правильный пароль к нему")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK пользователь авторизован, токен выдан"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Not Found пользователь не найден")
    })
    @PostMapping
    public ResponseEntity<JwtAuthResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto,
                                                     HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signIn(signInRequestDto));
    }
}
