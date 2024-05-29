package com.github.jon7even.bankingservice.mapper;

import com.github.jon7even.bankingservice.dto.user.UserFullResponseDto;
import com.github.jon7even.bankingservice.dto.user.UserShortResponseDto;
import com.github.jon7even.bankingservice.entity.UserEntity;
import com.github.jon7even.bankingservice.setup.PreparationObjectsForTests;
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
        UserMapperImpl.class
})
public class UserMapperTest extends PreparationObjectsForTests {

    @Autowired private UserMapper userMapper;

    @BeforeEach public void setupMapperTest() {
        initUserEntity();
        initUserCreateDto();
        initUserFullResponseDto();
        initUserShortResponseDto();
    }

    @DisplayName("Должен произойти правильный маппинг в сущность для создания новых пользователей в БД")
    @Test public void toEntityFromDtoCreate_Return_EntityWithNotId() {
        UserEntity actualResult = userMapper.toUserEntityFromCreateDto(userCreateDtoFirst, firstDateTime);

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userEntityFirstWithoutId);
    }

    @DisplayName("Должен произойти правильный маппинг в DTO для полного предоставления информации о пользователе")
    @Test public void toDtoFromEntity_Return_UserFullResponseDto() {
        UserFullResponseDto actualResult = userMapper.toUserFullDtoFromUserEntity(
                userEntityFirst, bankAccountShortResponseDto, emailShortResponseDtoFirst, phoneShortResponseDtoFirst
        );

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userFullResponseDtoFirst);
    }

    @DisplayName("Должен произойти правильный маппинг в DTO для краткого предоставления информации о пользователе")
    @Test public void toDtoFromEntity_Return_UserShortResponseDto() {
        UserShortResponseDto actualResult = userMapper.toUserShortDtoFromUserEntity(
                userEntityFirst, bankAccountShortResponseDto, emailShortResponseDtoFirst, phoneShortResponseDtoFirst
        );

        assertThat(actualResult)
                .isNotNull()
                .isEqualTo(userShortResponseDtoFirst);
    }
}
