package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * Метод для проверки существования телефона пользователя по списку номеров
     *
     * @param phones список номеров
     */
    @Query("   SELECT CASE WHEN COUNT(uph) > 0 "
            + "       THEN true"
            + "       ELSE false"
            + "       END "
            + "  FROM UserPhoneEntity AS uph "
            + " WHERE (COALESCE(:phones, NULL) IS NULL OR uph.phone IN (:phones) ) ")
    boolean existsPhonesBySetPhones(@Param("phones") Set<String> phones);
}
