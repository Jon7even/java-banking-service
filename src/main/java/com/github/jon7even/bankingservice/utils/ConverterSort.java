package com.github.jon7even.bankingservice.utils;

import com.github.jon7even.bankingservice.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.bankingservice.enums.user.UserSort;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static com.github.jon7even.bankingservice.constants.LogsMessage.*;

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
            log.debug("{} [{}]", SORT_NOT_REQUIRED, PARAMETER_EMAIL);
            return Optional.empty();
        }

        if (paramsDto.getPhone() != null && !paramsDto.getPhone().isBlank()) {
            log.debug("{} [{}]", SORT_NOT_REQUIRED, PARAMETER_PHONE);
            return Optional.empty();
        }

        if (paramsDto.getDateOfBirth() != null) {
            var userSortFromRequest = paramsDto.getSort();
            Sort sorting;
            if (userSortFromRequest == UserSort.USER_ASC) {
                log.debug("{} возрастанию {}]", SORT_REQUIRED, PARAMETER_DATE_OF_BIRTH);
                sorting = Sort.by(Sort.Direction.ASC, "dateOfBirth");
            } else {
                log.debug("{} убыванию [{}]", SORT_REQUIRED, PARAMETER_DATE_OF_BIRTH);
                sorting = Sort.by(Sort.Direction.DESC, "dateOfBirth");
            }
            return Optional.of(sorting);
        }

        var userSortFromRequest = paramsDto.getSort();
        Sort sorting;
        if (userSortFromRequest == UserSort.USER_ASC) {
            log.debug("{} возрастанию [user_id]", SORT_REQUIRED);
            sorting = Sort.by(Sort.Direction.ASC, "id");
        } else {
            log.debug("{} убыванию [user_id]", SORT_REQUIRED);
            sorting = Sort.by(Sort.Direction.DESC, "id");
        }

        return Optional.of(sorting);
    }
}
