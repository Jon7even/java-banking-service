package com.github.jon7even.service;

import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.entity.UserEntity;

/**
 * Интерфейс для взаимодействия с банковским счетом пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
public interface BankAccountService {
    /**
     * Метод для создания нового банковского счета для нового пользователя
     *
     * @param bankAccountCreateDto объект DTO с изначальной суммой
     * @param newUserEntity        пользователь, который будет являться владельцем счета
     * @return BankAccountShortResponseDto объект DTO
     */
    BankAccountShortResponseDto createBankAccount(BankAccountCreateDto bankAccountCreateDto, UserEntity newUserEntity);
}
