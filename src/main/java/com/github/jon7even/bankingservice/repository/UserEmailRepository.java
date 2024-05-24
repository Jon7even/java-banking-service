package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Интерфейс DAO для электронной почты пользователя(UserEmailEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface UserEmailRepository extends JpaRepository<UserEmailEntity, Long> {
    /**
     * Метод для проверки существования электронной почты пользователей по адресу
     *
     * @param email электронная почта
     */
    boolean existsByEmail(@Param("email") String email);

    /**
     * Метод для проверки существования пользователя по email
     *
     * @param emails список электронных адресов
     */
    @Query("   SELECT CASE WHEN COUNT(uem) > 0 "
            + "       THEN true"
            + "       ELSE false"
            + "       END "
            + "  FROM UserEmailEntity AS uem "
            + " WHERE (COALESCE(:emails, NULL) IS NULL OR uem.email IN (:emails) ) ")
    boolean existsEmailsBySetEmails(@Param("emails") Set<String> emails);
}
