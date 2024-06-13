package com.github.jon7even.service;

import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountFullResponseDto;
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
     * @return BankAccountFullResponseDto объект DTO c суммой, с которой открылся счет и ID транзакции
     */
    BankAccountFullResponseDto createBankAccount(BankAccountCreateDto bankAccountCreateDto, UserEntity newUserEntity);

    /**
     * Метод обновляющий статус транзакции (списание/пополнение денег по счету пользователя), если операция успешна
     *
     * @param transactionId ID транзакции
     */
    void setTransactionIsSuccess(Long transactionId);

    /**
     * Метод обновляющий статус транзакции (списание/пополнение денег по счету пользователя), если операция не прошла
     *
     * @param transactionId ID транзакции
     */
    void setTransactionIsFailure(Long transactionId);
}