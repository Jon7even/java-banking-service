package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для банковского счета пользователя(UserEmailEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
}
