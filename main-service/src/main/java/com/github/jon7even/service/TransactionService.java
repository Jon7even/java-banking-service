package com.github.jon7even.service;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.transaction.TransactionUpdateDto;

/**
 * Интерфейс для взаимодействия с транзакциями
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Используется внутренними сервисами приложения для логирования операций с банковским счетом пользователя
 */
public interface TransactionService {
    /**
     * Метод сохраняющий новую транзакцию
     *
     * @param transactionCreateDto заполненный объект DTO со всеми необходимыми полями
     * @return Long ID транзакции для будущего изменения статуса
     */
    Long saveNewTransaction(TransactionCreateDto transactionCreateDto);

    /**
     * Метод обновляющий статус транзакции
     *
     * @param transactionUpdateDto заполненный объект DTO с ID транзакции и новым статусом транзакции
     */
    void updateStatusTransaction(TransactionUpdateDto transactionUpdateDto);
}
