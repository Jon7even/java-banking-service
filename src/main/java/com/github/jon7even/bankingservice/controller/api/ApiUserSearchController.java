package com.github.jon7even.bankingservice.controller.api;

import com.github.jon7even.bankingservice.dto.user.UserShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.bankingservice.enums.user.UserSort;
import com.github.jon7even.bankingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.github.jon7even.bankingservice.constants.ControllerApi.PATH_API;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_SEARCH;
import static com.github.jon7even.bankingservice.constants.ControllerUser.PATH_USERS;
import static com.github.jon7even.bankingservice.constants.DateTimeFormat.DATE_DEFAULT;
import static com.github.jon7even.bankingservice.constants.DefaultValuesController.*;
import static com.github.jon7even.bankingservice.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Контроллер для поиска пользователей API эндпоинт "Пользователи"
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используется для поиска пользователей по параметрам. Требуется JWT токен для аутентификации
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = PATH_API + PATH_USERS + PATH_SEARCH)
public class ApiUserSearchController {
    private final UserService userService;

    @Operation(
            summary = "Поиск пользователей по параметрам",
            description = "Запросы на эндпоинт позволяют найти список пользователей по заданным критериям")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK список пользователей получен")
    })
    @GetMapping
    public ResponseEntity<List<UserShortResponseDto>> searchUserByParam(
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_DEFAULT) LocalDate dateOfBirth,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = PARAM_SORT_DEFAULT) UserSort sort,
            @RequestParam(name = PARAM_FROM, defaultValue = PARAM_FROM_DEFAULT) Integer from,
            @RequestParam(name = PARAM_SIZE, defaultValue = PARAM_SIZE_DEFAULT) Integer size,
            HttpServletRequest request) {

        log.debug("On {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod());

        return ResponseEntity.ok().body(userService.getListUsersByParam(
                ParamsSearchUserRequestDto.builder()
                        .dateOfBirth(dateOfBirth)
                        .phone(phone)
                        .firstName(firstName)
                        .lastName(lastName)
                        .middleName(middleName)
                        .email(email)
                        .sort(sort)
                        .from(from)
                        .size(size)
                        .build())
        );
    }
}