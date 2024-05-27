package com.github.jon7even.bankingservice.repository;

import com.github.jon7even.bankingservice.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * Метод для получения пользователей по электронной почте
     *
     * @param email    электронная почта пользователя
     * @param pageable заполненный объект пагинации и сортировки
     */
    @Query("   SELECT user"
            + "  FROM UserEntity AS user "
            + "  LEFT JOIN FETCH user.emails AS eml"
            + " WHERE eml.email = :email")
    List<UserEntity> getListUserByEmail(@Param("email") String email, Pageable pageable);

    /**
     * Метод для получения пользователей по номеру телефона
     *
     * @param phone    номер телефона пользователя
     * @param pageable заполненный объект пагинации и сортировки
     */
    @Query("   SELECT user"
            + "  FROM UserEntity AS user "
            + "  LEFT JOIN FETCH user.phones AS ph"
            + " WHERE ph.phone = :phone")
    List<UserEntity> getListUserByPhone(@Param("phone") String phone, Pageable pageable);

    /**
     * Метод для получения пользователей по дате рождения
     *
     * @param dateOfBirth дата рождения с которого будет работать выборка
     * @param pageable    заполненный объект пагинации и сортировки
     */
    @Query("   SELECT user"
            + "  FROM UserEntity AS user "
            + "  LEFT JOIN FETCH user.phones"
            + " WHERE user.dateOfBirth > :dateOfBirth")
    List<UserEntity> getListUserByAfterParamDate(@Param("dateOfBirth") LocalDate dateOfBirth, Pageable pageable);

    /**
     * Метод для получения пользователей по ФИО
     *
     * @param firstName  имя пользователя, необязательный параметр
     * @param lastName   фамилия пользователя, необязательный параметр
     * @param middleName отчество пользователя, необязательный параметр
     * @param pageable   заполненный объект пагинации и сортировки
     */
    @Query("    SELECT user"
            + "  FROM UserEntity AS user "
            + "  LEFT JOIN FETCH user.phones"
            + " WHERE LOWER(user.firstName)  LIKE LOWER(CONCAT (COALESCE(:firstName, ''), '%') )"
            + "   AND LOWER(user.lastName)   LIKE LOWER(CONCAT (COALESCE(:lastName,  ''), '%') )"
            + "   AND LOWER(user.middleName) LIKE LOWER(CONCAT (COALESCE(:middleName,''), '%') )")
    List<UserEntity> getListUserByLikeName(@Param("firstName") String firstName,
                                           @Param("lastName") String lastName,
                                           @Param("middleName") String middleName,
                                           Pageable pageable);
}