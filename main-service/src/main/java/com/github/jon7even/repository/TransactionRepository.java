package com.github.jon7even.repository;

import com.github.jon7even.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для работы с транзакциями (TransactionEntity)
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote Использует JpaRepository
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
