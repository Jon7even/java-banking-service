package com.github.jon7even.mapper;

import com.github.jon7even.dto.user.email.EmailCreateDto;
import com.github.jon7even.dto.user.email.EmailShortResponseDto;
import com.github.jon7even.entity.UserEmailEntity;
import com.github.jon7even.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Интерфейс для маппинга DTO и сущностей электронной почты пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserEmailMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "emailCreateDto.email", target = "email")
    @Mapping(target = "owner", ignore = true)
    List<UserEmailEntity> toListEntityEmailFromCreateDto(List<EmailCreateDto> emailCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "emailCreateDto.email", target = "email")
    @Mapping(source = "userEntity", target = "owner")
    UserEmailEntity toEntityEmailFromCreateDto(EmailCreateDto emailCreateDto, UserEntity userEntity);

    @Mapping(source = "userEmailEntities.email", target = "email")
    List<EmailShortResponseDto> toShortListEmailDtoFromEmailEntity(List<UserEmailEntity> userEmailEntities);

    @Mapping(source = "userEmailEntity.email", target = "email")
    EmailShortResponseDto toShortEmailDtoFromEmailEntity(UserEmailEntity userEmailEntity);
}
