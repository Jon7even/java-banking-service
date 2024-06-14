package com.github.jon7even.controller.api;

import com.github.jon7even.constants.ControllerApi;
import com.github.jon7even.constants.ControllerUser;
import com.github.jon7even.dto.user.transfer.TransferCreateDto;
import com.github.jon7even.dto.user.transfer.TransferResponseDto;
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

import static com.github.jon7even.constants.ControllerUser.PATH_USER_ID;
import static com.github.jon7even.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Контроллер для совершения денежных переводов между пользователями
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote API эндпоинт "Пользователи": Требуется JWT токен для аутентификации.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ControllerApi.PATH_API + ControllerUser.PATH_USERS)
public class ApiUserTransferController {
    private final UserService userService;


    @Operation(
            summary = "Совершить денежный перевод",
            description = "Перевести деньги на счет другого пользователя "
                    + "требуется заполненный объект TransferCreateDto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created Перевод совершен"),
            @ApiResponse(responseCode = "400", description = "Bad Request ошибки в случае неправильного запроса"),
            @ApiResponse(responseCode = "403", description = "Forbidden пользователь не авторизован")
    })
    @PostMapping(PATH_USER_ID + ControllerUser.PATH_TRANSFER)
    public ResponseEntity<TransferResponseDto> transfer(@PathVariable @Positive Long userId,
                                                        @Valid @RequestBody TransferCreateDto transferCreateDto,
                                                        HttpServletRequest request) {
        log.debug("На {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.transferByOwner(transferCreateDto, userId));
    }
}
