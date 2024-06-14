package com.github.jon7even.service;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.UserShortResponseDto;
import com.github.jon7even.dto.user.search.ParamsSearchUserRequestDto;
import com.github.jon7even.dto.user.transfer.TransferCreateDto;
import com.github.jon7even.dto.user.transfer.TransferResponseDto;

import java.util.List;

/**
 * Интерфейс для взаимодействия с пользователями
 *
 * @author Jon7even
 * @version 1.0
 */
public interface UserService {
    /**
     * Метод регистрирующий нового пользователя в системе
     *
     * @param userCreateDto заполненный объект DTO с данными пользователя
     * @return UserFullResponseDto объект DTO с подробными данными о пользователе
     */
    UserFullResponseDto createUser(UserCreateDto userCreateDto);

    /**
     * Метод поиска пользователей в системе
     *
     * @param paramsSearchUserRequestDto объект DTO с параметрами поиска
     * @return UserShortResponseDto список объектов DTO с краткими данными о пользователе
     */
    List<UserShortResponseDto> getListUsersByParam(ParamsSearchUserRequestDto paramsSearchUserRequestDto);

    /**
     * Метод для совершения трансфера денег с одного банковского счета на другой
     *
     * @param transferCreateDto  заполненный объект DTO с ID счета получателя и суммой
     * @param ownerBankAccountId ID владельца счета совершающий перевод
     * @return TransferResponseDto объект DTO с данными о совершении перевода
     */
    TransferResponseDto transferByOwner(TransferCreateDto transferCreateDto, Long ownerBankAccountId);
}
