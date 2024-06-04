package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.email.EmailUpdateDto;
import com.github.jon7even.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.jon7even.constants.ControllerUser.*;
import static com.github.jon7even.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Контроллер для работы с электронной почтой пользователя
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote API эндпоинт "Пользователи": Требуется JWT токен для аутентификации.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ControllerApi.PATH_API + PATH_USERS)
public class ApiUserEmailController {
    private final UserService userService;

    @Operation(
            summary = "Добавить новый email",
            description = "Добавление в профиль пользователя нового email,требуется заполненный объект EmailCreateDto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created новый электронный адрес успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @PostMapping(PATH_USER_ID + PATH_EMAIL)
    public ResponseEntity<EmailShortResponseDto> create(@PathVariable @Positive Long userId,
                                                        @Valid @RequestBody EmailCreateDto emailCreateDto,
                                                        HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewEmail(emailCreateDto, userId));
    }

    @Operation(
            summary = "Обновить существующий email",
            description = "Обновить email у пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content email успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @PatchMapping(PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID)
    public ResponseEntity<Void> update(@PathVariable @Positive Long userId,
                                       @Valid @RequestBody EmailUpdateDto emailUpdateDto,
                                       HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        userService.updateEmailById(emailUpdateDto, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Удалить существующий email",
            description = "Удалить из профиля пользователя существующий email")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content email успешно удален"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @DeleteMapping(PATH_USER_ID + PATH_EMAIL + PATH_EMAIL_ID)
    public ResponseEntity<Void> delete(@PathVariable @Positive Long userId,
                                       @PathVariable @Positive Long emailId,
                                       HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        userService.deleteEmailById(userId, emailId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
