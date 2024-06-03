package com.github.jon7even.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jon7even.exception.model.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.github.jon7even.constants.LogsMessage.ACCESS_DENIED;
import static com.github.jon7even.constants.LogsMessage.IN_CONTROLLER_METHOD;

/**
 * Компонент для перехвата точки входа фильтра
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Перехват выполняется если пользователь не авторизован
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.debug("On {} {} {} {}", request.getRequestURL(), IN_CONTROLLER_METHOD, request.getMethod(), ACCESS_DENIED);
        log.error("Для запроса{} [{}]", ACCESS_DENIED, request);

        ApiError responseException = ApiError.builder()
                .status("FORBIDDEN")
                .reason("Access denied.")
                .message("На этой странице" + ACCESS_DENIED + ", авторизуйтесь по адресу /auth")
                .timestamp(LocalDateTime.now())
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(objectMapper.writeValueAsString(responseException));
    }
}
