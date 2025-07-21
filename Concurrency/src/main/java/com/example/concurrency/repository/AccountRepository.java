package com.example.concurrency.repository;

import com.example.concurrency.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // 비관적 락 테스트
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdForUpdateNoLock(@Param("id") Long id);

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdForUpdatePositiveLock(@Param("id") Long id);

}
