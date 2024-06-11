package com.github.jon7even.mapper;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.transaction.TransactionUpdateDto;
import com.github.jon7even.entity.TransactionEntity;
import com.github.jon7even.enums.transaction.TransactionStatus;
import com.github.jon7even.enums.transaction.TransactionType;
import com.github.jon7even.setup.PreparationObjectsForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TransactionMapperImpl.class
})
public class TransactionMapperTest extends PreparationObjectsForTests {
    @Autowired private TransactionMapper transactionMapper;
    private TransactionCreateDto transactionCreateDto;
    private TransactionEntity transactionEntity;

    @BeforeEach public void setupMapperTest() {
        initUserEntity();

        transactionCreateDto = TransactionCreateDto.builder()
                .accountId(firstId)
                .from("TERMINAL_TEST")
                .amount(balanceFirst)
                .type(TransactionType.TOPUP)
                .build();

        transactionEntity = TransactionEntity.builder()
                .target(bankAccountEntityFirst)
                .from("TERMINAL_TEST")
                .amount(balanceFirst)
                .status(TransactionStatus.QUEUE)
                .type(TransactionType.TOPUP)
                .createdOn(firstDateTime)
                .build();
    }

    @DisplayName("Должен произойти правильный маппинг в сущность для создания новой транзакции в БД")
    @Test public void toEntityFromDtoCreate_Return_EntityWithNotId() {
        TransactionEntity actualResult = transactionMapper.toTransactionEntityFromCreateDto(
                transactionCreateDto, bankAccountEntityFirst, firstDateTime
        );

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(transactionEntity);
    }

    @DisplayName("Должен произойти правильный маппинг в сущность для обновления существующей транзакции в БД")
    @Test public void updateTransactionEntityFromUpdateDto_Return_EntityUpdatedWithNewStatus() {
        TransactionUpdateDto transactionUpdateDto = TransactionUpdateDto.builder()
                .transactionId(firstId)
                .status(TransactionStatus.SUCCESS)
                .build();

        transactionMapper.updateTransactionEntityFromUpdateDto(transactionEntity, transactionUpdateDto, secondDateTime);

        TransactionEntity expected = TransactionEntity.builder()
                .target(bankAccountEntityFirst)
                .from("TERMINAL_TEST")
                .amount(balanceFirst)
                .status(TransactionStatus.SUCCESS)
                .type(TransactionType.TOPUP)
                .createdOn(firstDateTime)
                .updatedOn(secondDateTime)
                .build();

        assertThat(transactionEntity)
                .isNotNull()
                .isEqualTo(expected);
    }
}
