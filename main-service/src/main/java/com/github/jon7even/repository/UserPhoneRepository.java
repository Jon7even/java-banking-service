package com.github.jon7even.repository;

import com.github.jon7even.entity.UserPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс DAO для номеров телефона пользователя(UserPhoneEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface UserPhoneRepository extends JpaRepository<UserPhoneEntity, Long> {
    /**
     * Метод для проверки существования телефона пользователя по номеру
     *
     * @param phone номер телефона
     */
    boolean existsByPhone(@Param("phone") String phone);

    /**
     * Метод для получения списка сущностей номеров телефона по списку из номеров
     *
     * @param phones список из номеров
     */
    @Query("   SELECT uph"
            + "  FROM UserPhoneEntity AS uph "
            + " WHERE uph.phone IN :phones")
    List<UserPhoneEntity> getPhoneEntityBySetPhones(@Param("phones") Set<String> phones);

    /**
     * Метод получения списка всех номеров телефона пользователя по его ID
     *
     * @param ownerId ID пользователя
     */
    List<UserPhoneEntity> findByOwnerId(@Param("ownerId") Long ownerId);
}
