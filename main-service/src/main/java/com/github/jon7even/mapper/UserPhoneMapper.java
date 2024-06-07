package com.github.jon7even.mapper;

import com.github.jon7even.dto.user.phone.PhoneCreateDto;
import com.github.jon7even.dto.user.phone.PhoneShortResponseDto;
import com.github.jon7even.entity.UserEntity;
import com.github.jon7even.entity.UserPhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей номеров телефона пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserPhoneMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "phoneCreateDto.phone", target = "phone")
    @Mapping(target = "owner", ignore = true)
    List<UserPhoneEntity> toListEntityPhoneFromCreateDto(List<PhoneCreateDto> phoneCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "phoneCreateDto.phone", target = "phone")
    @Mapping(source = "userEntity", target = "owner")
    UserPhoneEntity toEntityPhoneFromCreateDto(PhoneCreateDto phoneCreateDto, UserEntity userEntity);

    @Mapping(source = "userPhoneEntities.phone", target = "phone")
    List<PhoneShortResponseDto> toShortListPhoneDtoFromPhoneEntity(List<UserPhoneEntity> userPhoneEntities);

    @Mapping(source = "userPhoneEntity.phone", target = "phone")
    PhoneShortResponseDto toShortPhoneDtoFromPhoneEntity(UserPhoneEntity userPhoneEntity);
}
