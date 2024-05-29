package com.github.jon7even.utils;

import com.github.jon7even.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.enums.user.UserSort;
import com.github.jon7even.constants.LogsMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * Утилитарный класс для конвертации параметра "сортировка" из ParamsSearchUserRequestDto
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterSort {
    public static Optional<Sort> getSortingByParamsSearchUserRequest(ParamsSearchUserRequestDto paramsDto) {
        log.debug("Начинаем получать тип сортировки по [{}]", paramsDto);
        if (paramsDto.getEmail() != null && !paramsDto.getEmail().isBlank()) {
            log.debug("{} [{}]", LogsMessage.SORT_NOT_REQUIRED, LogsMessage.PARAMETER_EMAIL);
            return Optional.empty();
        }

        if (paramsDto.getPhone() != null && !paramsDto.getPhone().isBlank()) {
            log.debug("{} [{}]", LogsMessage.SORT_NOT_REQUIRED, LogsMessage.PARAMETER_PHONE);
            return Optional.empty();
        }

        if (paramsDto.getDateOfBirth() != null) {
            var userSortFromRequest = paramsDto.getSort();
            Sort sorting;
            if (userSortFromRequest == UserSort.USER_ASC) {
                log.debug("{} возрастанию {}]", LogsMessage.SORT_REQUIRED, LogsMessage.PARAMETER_DATE_OF_BIRTH);
                sorting = Sort.by(Sort.Direction.ASC, "dateOfBirth");
            } else {
                log.debug("{} убыванию [{}]", LogsMessage.SORT_REQUIRED, LogsMessage.PARAMETER_DATE_OF_BIRTH);
                sorting = Sort.by(Sort.Direction.DESC, "dateOfBirth");
            }
            return Optional.of(sorting);
        }

        var userSortFromRequest = paramsDto.getSort();
        Sort sorting;
        if (userSortFromRequest == UserSort.USER_ASC) {
            log.debug("{} возрастанию [user_id]", LogsMessage.SORT_REQUIRED);
            sorting = Sort.by(Sort.Direction.ASC, "id");
        } else {
            log.debug("{} убыванию [user_id]", LogsMessage.SORT_REQUIRED);
            sorting = Sort.by(Sort.Direction.DESC, "id");
        }

        return Optional.of(sorting);
    }
}
