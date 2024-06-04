package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneUpdateDto;
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
 * Контроллер для работы с номером телефона пользователя
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote API эндпоинт "Пользователи": Требуется JWT токен для аутентификации.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ControllerApi.PATH_API + PATH_USERS)
public class ApiUserPhoneController {
    private final UserService userService;

    @Operation(
            summary = "Добавить новый phone",
            description = "Добавление в профиль пользователя нового номера телефона, "
                    + "требуется заполненный объект PhoneCreateDto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created phone успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @PostMapping(PATH_USER_ID + PATH_PHONE)
    public ResponseEntity<PhoneShortResponseDto> create(@PathVariable @Positive Long userId,
                                                        @Valid @RequestBody PhoneCreateDto phoneCreateDto,
                                                        HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewPhone(phoneCreateDto, userId));
    }

    @Operation(
            summary = "Обновить существующий phone",
            description = "Обновить номер телефона у пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content phone успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @PatchMapping(PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID)
    public ResponseEntity<PhoneShortResponseDto> update(@PathVariable @Positive Long userId,
                                                        @Valid @RequestBody PhoneUpdateDto phoneUpdateDto,
                                                        HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.updatePhoneById(phoneUpdateDto, userId));
    }

    @Operation(
            summary = "Удалить существующий phone",
            description = "Удалить из профиля пользователя существующий номер телефона")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content phone успешно удален"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @DeleteMapping(PATH_USER_ID + PATH_PHONE + PATH_PHONE_ID)
    public ResponseEntity<Void> delete(@PathVariable @Positive Long userId,
                                       @PathVariable @Positive Long phoneId,
                                       HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        userService.deletePhoneById(userId, phoneId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
