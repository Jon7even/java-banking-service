package com.github.jon7even.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * Утилитарный класс для конвертации параметра "пагинации" из параметров
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterPageable {
    public static PageRequest getPageRequestByParams(int from, int size, Optional<Sort> sort) {
        log.debug("Начинаем формировать пагинацию: [from={}], [size={}], [sort={}]", from, size, sort);
        boolean isExistParamSort = sort.isPresent();
        int pageResponse = from / size;

        if (isExistParamSort) {
            return PageRequest.of(pageResponse, size, sort.get());
        } else {
            return PageRequest.of(pageResponse, size);
        }
    }
}
