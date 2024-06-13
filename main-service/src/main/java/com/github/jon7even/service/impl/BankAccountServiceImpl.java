package com.github.jon7even.service.impl;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.transaction.TransactionUpdateDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountFullResponseDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.enums.transaction.TransactionStatus;
import com.github.jon7even.enums.transaction.TransactionType;
import com.github.jon7even.exception.IncorrectMadeRequestException;
import com.github.jon7even.mapper.BankAccountMapper;
import com.github.jon7even.repository.BankAccountRepository;
import com.github.jon7even.service.BankAccountService;
import com.github.jon7even.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.github.jon7even.constants.LogsMessage.*;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

/**
 * Реализация сервиса взаимодействия с банковскими счетами пользователей
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final TransactionService transactionService;

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = SERIALIZABLE)
    public BankAccountFullResponseDto createBankAccount(BankAccountCreateDto bankAccountCreateDto,
                                                        UserEntity newUserEntity) {
        log.trace(SAVE_IN_REPOSITORY + "[bankAccountCreateDto={}]", bankAccountCreateDto);
        checkBankAccount(bankAccountCreateDto);

        BankAccountEntity bankAccountForSaveInRepository = bankAccountMapper.toEntityBankAccountFromCreateDto(
                bankAccountCreateDto, newUserEntity
        );

        BankAccountEntity savedBankAccountInRepository =
                bankAccountRepository.saveAndFlush(bankAccountForSaveInRepository);

        log.trace("Новый банковский аккаунт успешно создан [Bank Account={}]", savedBankAccountInRepository);

        Long transactionId = createTransactionTopup(
                savedBankAccountInRepository.getId(), bankAccountCreateDto.getBalance(), "SYSTEM"
        );
        return bankAccountMapper.toFullBalanceDtoFromBankAccountEntity(savedBankAccountInRepository, transactionId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = SERIALIZABLE)
    public void setTransactionIsSuccess(Long transactionId) {
        updateTransaction(transactionId, TransactionStatus.SUCCESS);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, isolation = SERIALIZABLE)
    public void setTransactionIsFailure(Long transactionId) {
        updateTransaction(transactionId, TransactionStatus.FAILURE);
    }

    private void updateTransaction(Long transactionId, TransactionStatus status) {
        TransactionUpdateDto transactionForUpdate = TransactionUpdateDto.builder()
                .transactionId(transactionId)
                .status(status)
                .build();
        transactionService.updateStatusTransaction(transactionForUpdate);
    }

    private Long createTransactionTopup(Long accountId, BigDecimal amount, String from) {
        TransactionCreateDto transactionForSave = TransactionCreateDto.builder()
                .accountId(accountId)
                .from(from)
                .amount(amount)
                .type(TransactionType.TOPUP)
                .build();
        return transactionService.saveNewTransaction(transactionForSave);
    }

    private void checkBankAccount(BankAccountCreateDto bankAccountCreateDto) {
        log.trace("{} объект DTO для открытия счета перед сохранением в БД", START_CHECKING);
        var balance = bankAccountCreateDto.getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            log.error(PARAMETER_BAD_REQUEST + PARAMETER_BALANCE + WRONG_CAN_NOT + "отрицательным");
            throw new IncorrectMadeRequestException(PARAMETER_BALANCE, WRONG_CAN_NOT + "отрицательным");
        }
        log.debug("{} [банковский аккаунт]", END_CHECKING);
    }
}