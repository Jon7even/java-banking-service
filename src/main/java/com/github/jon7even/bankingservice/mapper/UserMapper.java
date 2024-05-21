package com.github.jon7even.bankingservice.mapper;

import com.github.jon7even.bankingservice.dto.user.UserCreateDto;
import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

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
    @Mapping(source = "userCreateDto.email", target = "email")
    @Mapping(source = "userCreateDto.password", target = "password")
    @Mapping(source = "userCreateDto.phone", target = "phone")
    @Mapping(constant = "false", target = "isConfirmedPhone")
    @Mapping(source = "userCreateDto.firstName", target = "firstName")
    @Mapping(source = "userCreateDto.lastName", target = "lastName")
    @Mapping(source = "userCreateDto.middleName", target = "middleName")
    @Mapping(source = "userCreateDto.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "now", target = "registeredOn")
    @Mapping(target = "updatedOn", ignore = true)
    UserEntity toEntityFromCreateDto(UserCreateDto userCreateDto, LocalDateTime now);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.login", target = "login")
    @Mapping(source = "userEntity.email", target = "email")
    @Mapping(source = "userEntity.phone", target = "phone")
    @Mapping(source = "userEntity.isConfirmedPhone", target = "isConfirmedPhone")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.middleName", target = "middleName")
    @Mapping(source = "userEntity.dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "userEntity.registeredOn", target = "registeredOn")
    @Mapping(source = "userEntity.updatedOn", target = "updatedOn")
    UserFullResponseDto toFullDtoFromEntity(UserEntity userEntity);
}
