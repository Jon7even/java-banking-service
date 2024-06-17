package com.github.jon7even.service.impl;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.transaction.TransactionUpdateDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.TransactionEntity;
import com.github.jon7even.exception.EntityNotFoundException;
import com.github.jon7even.mapper.TransactionMapper;
import com.github.jon7even.repository.BankAccountRepository;
import com.github.jon7even.repository.TransactionRepository;
import com.github.jon7even.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.github.jon7even.constants.LogsMessage.SAVE_IN_REPOSITORY;
import static com.github.jon7even.constants.LogsMessage.UPDATE_IN_REPOSITORY;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

/**
 * Реализация сервиса взаимодействия с транзакциями
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = REPEATABLE_READ)
    public Long saveNewTransaction(TransactionCreateDto transactionCreateDto) {
        log.trace(SAVE_IN_REPOSITORY + "[transactionCreateDto={}]", transactionCreateDto);

        var bankAccountId = transactionCreateDto.getAccountId();
        BankAccountEntity bankAccountEntity = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Аккаунт с [id=%d]", bankAccountId)));

        TransactionEntity transactionEntityForSaveInRepository = transactionMapper.toTransactionEntityFromCreateDto(
                transactionCreateDto, bankAccountEntity, LocalDateTime.now()
        );

        TransactionEntity savedTransaction = transactionRepository.saveAndFlush(transactionEntityForSaveInRepository);
        var transactionId = savedTransaction.getId();
        log.trace("В БД успешно сохранена новая транзакция: [{}] c [ID={}]", savedTransaction, transactionId);
        return transactionId;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = REPEATABLE_READ)
    public void updateStatusTransaction(TransactionUpdateDto transactionUpdateDto) {
        log.debug(UPDATE_IN_REPOSITORY + "[transactionUpdateDto={}]", transactionUpdateDto);

        var transactionId = transactionUpdateDto.getTransactionId();
        TransactionEntity transactionEntityForUpdate = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Транзакция с [id=%d]", transactionId)));

        transactionMapper.updateTransactionEntityFromUpdateDto(
                transactionEntityForUpdate, transactionUpdateDto, LocalDateTime.now()
        );

        TransactionEntity updatedTransaction = transactionRepository.saveAndFlush(transactionEntityForUpdate);
        log.trace("В БД обновлен статус транзакции: [{}]", updatedTransaction);
    }
}
