package com.github.jon7even.bankingservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс значений параметров по умолчанию, использующихся в эндпоинтах
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultValuesController {
    public static final String PARAM_FROM = "from";
    public static final String PARAM_FROM_DEFAULT = "0";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_SIZE_DEFAULT = "10";
    public static final String PARAM_SORT_DEFAULT = "USER_ASC";
}
