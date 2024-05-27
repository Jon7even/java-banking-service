package com.github.jon7even.bankingservice.controller.system;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.jon7even.bankingservice.constants.ControllerApi.PATH_SYSTEM;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_USERS;
import static com.github.jon7even.bankingservice.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Контроллер для служебного эндпоинта "Пользователи"
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используется для регистрации новых пользователей, операции удаления не предусмотрено, заранее предполагаем,
 * что это плохо:), JWT токен для этого эндпоинта не требуется
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH_SYSTEM + PATH_USERS)
public class SystemUserController {
    private final UserService userService;

    @Operation(
            summary = "Добавить нового пользователя",
            description = "Для создания нового пользователя требуется заполненный объект UserCreateDto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created пользователь создан")
    })
    @PostMapping
    public ResponseEntity<UserFullResponseDto> create(@RequestBody UserCreateDto userCreateDto,
                                                      HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateDto));
    }
}
