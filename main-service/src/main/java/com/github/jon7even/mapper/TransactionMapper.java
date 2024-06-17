package com.github.jon7even.mapper;

import com.github.jon7even.dto.transaction.TransactionCreateDto;
import com.github.jon7even.dto.transaction.TransactionUpdateDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

/**
 * Интерфейс для маппинга DTO и сущностей транзакций
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankAccountEntity", target = "target")
    @Mapping(source = "transactionCreateDto.from", target = "fromWhom")
    @Mapping(source = "transactionCreateDto.amount", target = "amount")
    @Mapping(constant = "QUEUE", target = "status")
    @Mapping(source = "transactionCreateDto.type", target = "type")
    @Mapping(source = "now", target = "createdOn")
    @Mapping(target = "updatedOn", ignore = true)
    TransactionEntity toTransactionEntityFromCreateDto(TransactionCreateDto transactionCreateDto,
                                                       BankAccountEntity bankAccountEntity,
                                                       LocalDateTime now);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "target", ignore = true)
    @Mapping(target = "fromWhom", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(source = "transactionUpdateDto.status", target = "status")
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(source = "now", target = "updatedOn")
    void updateTransactionEntityFromUpdateDto(@MappingTarget TransactionEntity transactionEntity,
                                              TransactionUpdateDto transactionUpdateDto,
                                              LocalDateTime now);
}
