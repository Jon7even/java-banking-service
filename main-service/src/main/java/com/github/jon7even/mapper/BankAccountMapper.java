package com.github.jon7even.mapper;

import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для маппинга DTO и сущностей банковского аккаунта
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankAccountCreateDto.balance", target = "balance")
    @Mapping(source = "ownerEntity", target = "owner")
    BankAccountEntity toEntityBankAccountFromCreateDto(BankAccountCreateDto bankAccountCreateDto,
                                                       UserEntity ownerEntity);

    @Mapping(source = "bankAccountEntity.balance", target = "balance")
    BankAccountShortResponseDto toShortBalanceDtoFromBankAccountEntity(BankAccountEntity bankAccountEntity);
}
