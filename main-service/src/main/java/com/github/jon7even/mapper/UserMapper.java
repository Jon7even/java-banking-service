package com.github.jon7even.mapper;

import com.github.jon7even.dto.user.UserCreateDto;
import com.github.jon7even.dto.user.UserFullResponseDto;
import com.github.jon7even.dto.user.UserShortResponseDto;
import com.github.jon7even.dto.user.account.BankAccountCreateDto;
import com.github.jon7even.dto.user.account.BankAccountShortResponseDto;
import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.entity.BankAccountEntity;
import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.entity.UserPhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userCreateDto.login", target = "login")
    @Mapping(source = "userCreateDto.password", target = "password")
    @Mapping(target = "bankAccountEntity", ignore = true)
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(source = "userCreateDto.firstName", target = "firstName")
    @Mapping(source = "userCreateDto.lastName", target = "lastName")
    @Mapping(source = "userCreateDto.middleName", target = "middleName")
    @Mapping(source = "userCreateDto.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "now", target = "registeredOn")
    @Mapping(target = "updatedOn", ignore = true)
    UserEntity toUserEntityFromCreateDto(UserCreateDto userCreateDto,
                                         LocalDateTime now);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.login", target = "login")
    @Mapping(source = "bankAccountShortResponseDto", target = "bankAccount")
    @Mapping(source = "emailShortResponseDto", target = "emails")
    @Mapping(source = "phoneShortResponseDto", target = "phones")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.middleName", target = "middleName")
    @Mapping(source = "userEntity.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "userEntity.registeredOn", target = "registeredOn")
    @Mapping(source = "userEntity.updatedOn", target = "updatedOn")
    UserFullResponseDto toUserFullDtoFromUserEntity(UserEntity userEntity,
                                                    BankAccountShortResponseDto bankAccountShortResponseDto,
                                                    List<EmailShortResponseDto> emailShortResponseDto,
                                                    List<PhoneShortResponseDto> phoneShortResponseDto);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.login", target = "login")
    @Mapping(source = "bankAccountShortResponseDto", target = "bankAccount")
    @Mapping(source = "emailShortResponseDto", target = "emails")
    @Mapping(source = "phoneShortResponseDto", target = "phones")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.middleName", target = "middleName")
    @Mapping(source = "userEntity.dateOfBirth", target = "dateOfBirth")
    UserShortResponseDto toUserShortDtoFromUserEntity(UserEntity userEntity,
                                                      BankAccountShortResponseDto bankAccountShortResponseDto,
                                                      List<EmailShortResponseDto> emailShortResponseDto,
                                                      List<PhoneShortResponseDto> phoneShortResponseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "emailCreateDto.email", target = "email")
    @Mapping(target = "owner", ignore = true)
    List<UserEmailEntity> toEntityEmailFromCreateDto(List<EmailCreateDto> emailCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "phoneCreateDto.phone", target = "phone")
    @Mapping(target = "owner", ignore = true)
    List<UserPhoneEntity> toEntityPhoneFromCreateDto(List<PhoneCreateDto> phoneCreateDto);

    @Mapping(source = "userEmailEntities.email", target = "email")
    List<EmailShortResponseDto> toShortEmailDtoFromEmailEntity(List<UserEmailEntity> userEmailEntities);

    @Mapping(source = "userPhoneEntities.phone", target = "phone")
    List<PhoneShortResponseDto> toShortPhoneDtoFromPhoneEntity(List<UserPhoneEntity> userPhoneEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "bankAccountCreateDto.balance", target = "balance")
    @Mapping(source = "ownerEntity", target = "owner")
    BankAccountEntity toEntityBankAccountFromCreateDto(BankAccountCreateDto bankAccountCreateDto,
                                                       UserEntity ownerEntity);

    @Mapping(source = "bankAccountEntity.balance", target = "balance")
    BankAccountShortResponseDto toShortBalanceDtoFromBankAccountEntity(BankAccountEntity bankAccountEntity);
}
