package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для пользователя(UserEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Метод для проверки существования пользователя по login
     *
     * @param login логин
     */
    boolean existsByLogin(@Param("login") String login);
}
