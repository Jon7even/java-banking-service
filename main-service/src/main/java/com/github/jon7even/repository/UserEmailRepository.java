package com.github.jon7even.repository;

import com.github.jon7even.entity.UserEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс DAO для электронной почты пользователя(UserEmailEntity)
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Использует JpaRepository
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
     * Метод для получения списка сущностей электронных адресов по списку из адресов
     *
     * @param emails список адресов
     */
    @Query("   SELECT uem"
            + "  FROM UserEmailEntity AS uem "
            + " WHERE uem.email IN :emails")
    List<UserEmailEntity> getEmailEntityBySetEmails(@Param("emails") Set<String> emails);

    /**
     * Метод получения списка всех электронных адресов пользователя по его ID
     *
     * @param ownerId ID пользователя
     */
    List<UserEmailEntity> findByOwnerId(@Param("ownerId") Long ownerId);
}
