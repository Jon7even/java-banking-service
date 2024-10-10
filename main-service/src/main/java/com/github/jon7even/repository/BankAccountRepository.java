package com.github.jon7even.repository;

import com.github.jon7even.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для работы с банковским счетом пользователя(BankAccountEntity)
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Использует JpaRepository
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
}
