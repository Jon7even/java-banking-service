package com.github.jon7even.service.impl;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.enums.transaction.TransactionType;
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

import static com.github.jon7even.constants.LogsMessage.SAVE_IN_REPOSITORY;

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
    @Transactional(propagation = Propagation.MANDATORY)
    public BankAccountShortResponseDto createBankAccount(BankAccountCreateDto bankAccountCreateDto,
                                                         UserEntity newUserEntity) {
        log.trace(SAVE_IN_REPOSITORY + "[bankAccountCreateDto={}]", bankAccountCreateDto);

        BankAccountEntity bankAccountForSaveInRepository = bankAccountMapper.toEntityBankAccountFromCreateDto(
                bankAccountCreateDto, newUserEntity
        );

        BankAccountEntity savedBankAccountInRepository =
                bankAccountRepository.saveAndFlush(bankAccountForSaveInRepository);
        createTransactionTopup(savedBankAccountInRepository.getId(), bankAccountCreateDto.getBalance(), "SYSTEM");

        log.trace("Новый банковский аккаунт успешно создан [Bank Account={}]", savedBankAccountInRepository);
        return bankAccountMapper.toShortBalanceDtoFromBankAccountEntity(savedBankAccountInRepository);
    }

    private void createTransactionTopup(Long accountId, BigDecimal amount, String from) {
        TransactionCreateDto transactionForSave = TransactionCreateDto.builder()
                .accountId(accountId)
                .from(from)
                .amount(amount)
                .type(TransactionType.TOPUP)
                .build();
        transactionService.saveNewTransaction(transactionForSave);
    }
}