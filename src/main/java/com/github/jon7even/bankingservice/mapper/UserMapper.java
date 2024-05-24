package com.github.jon7even.bankingservice.mapper;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailCreateDto;
import com.github.jon7even.bankingservice.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.bankingservice.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.bankingservice.entity.UserEmailEntity;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.entity.UserPhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Set;

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
    @Mapping(source = "userEmailEntities", target = "emails")
    @Mapping(source = "userPhoneEntities", target = "phones")
    @Mapping(source = "userCreateDto.firstName", target = "firstName")
    @Mapping(source = "userCreateDto.lastName", target = "lastName")
    @Mapping(source = "userCreateDto.middleName", target = "middleName")
    @Mapping(source = "userCreateDto.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "now", target = "registeredOn")
    @Mapping(target = "updatedOn", ignore = true)
    UserEntity toUserEntityFromCreateDto(UserCreateDto userCreateDto,
                                         Set<UserEmailEntity> userEmailEntities,
                                         Set<UserPhoneEntity> userPhoneEntities,
                                         LocalDateTime now);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.login", target = "login")
    @Mapping(source = "emailShortResponseDto", target = "emails")
    @Mapping(source = "phoneShortResponseDto", target = "phones")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.middleName", target = "middleName")
    @Mapping(source = "userEntity.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "userEntity.registeredOn", target = "registeredOn")
    @Mapping(source = "userEntity.updatedOn", target = "updatedOn")
    UserFullResponseDto toUserFullDtoFromUserEntity(UserEntity userEntity,
                                                    Set<EmailShortResponseDto> emailShortResponseDto,
                                                    Set<PhoneShortResponseDto> phoneShortResponseDto);

    @Mapping(source = "emailCreateDto.email", target = "email")
    Set<UserEmailEntity> toEntityEmailFromCreateDto(Set<EmailCreateDto> emailCreateDto);

    @Mapping(source = "phoneCreateDto.phone", target = "phone")
    Set<UserPhoneEntity> toEntityPhoneFromCreateDto(Set<PhoneCreateDto> phoneCreateDto);

    @Mapping(source = "userEmailEntities.email", target = "email")
    Set<EmailShortResponseDto> toShortEmailDtoFromEmailEntity(Set<UserEmailEntity> userEmailEntities);

    @Mapping(source = "userPhoneEntities.phone", target = "phone")
    Set<PhoneShortResponseDto> toShortPhoneDtoFromPhoneEntity(Set<UserPhoneEntity> userPhoneEntities);
}
