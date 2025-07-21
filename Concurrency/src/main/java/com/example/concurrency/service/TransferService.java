package com.example.concurrency.service;

import com.example.concurrency.entity.Account;
import com.example.concurrency.entity.TransferLog;
import com.example.concurrency.entity.User;
import com.example.concurrency.repository.AccountRepository;
import com.example.concurrency.repository.TransferLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Log4j2
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;
    private final TransferLogRepository transferLogRepository;
    @Transactional
    public void transferWithPessimisticLock(Long fromId, Long toId, Long amount) {
        Account from = accountRepository.findByIdForUpdate(fromId)
                .orElseThrow(() -> new RuntimeException("출금 계좌 없음"));
        Account to = accountRepository.findByIdForUpdate(toId)
                .orElseThrow(() -> new RuntimeException("입금 계좌 없음"));

        if (from.getBalance() < amount) {
            throw new RuntimeException("잔액 부족");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);


        // 로그 저장
        User fromUser = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("보낸 사용자 없음")).getOwner();
        User toUser = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("받은 사용자 없음")).getOwner();

        TransferLog log = TransferLog.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .transferBalance(amount)
                .fromAccountBalance(from.getBalance())
                .build();

        transferLogRepository.save(log);


    }

    @Transactional
    public void transferWithPositiveLock(Long fromId, Long toId, Long amount) {
        Account from = accountRepository.findByIdForUpdatePositiveLock(fromId)
                .orElseThrow(() -> new RuntimeException("출금 계좌 없음"));
        Account to = accountRepository.findByIdForUpdatePositiveLock(toId)
                .orElseThrow(() -> new RuntimeException("입금 계좌 없음"));

        if (from.getBalance() < amount) {
            throw new RuntimeException("잔액 부족");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);


        // 로그 저장
        User fromUser = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("보낸 사용자 없음")).getOwner();
        User toUser = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("받은 사용자 없음")).getOwner();

        TransferLog log = TransferLog.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .transferBalance(amount)
                .fromAccountBalance(from.getBalance())
                .build();

        transferLogRepository.save(log);


    }

    @Transactional
    public void transferWithNoLock(Long fromId, Long toId, Long amount) {
        Account from = accountRepository.findByIdForUpdateNoLock(fromId)
                .orElseThrow(() -> new RuntimeException("출금 계좌 없음"));
        Account to = accountRepository.findByIdForUpdateNoLock(toId)
                .orElseThrow(() -> new RuntimeException("입금 계좌 없음"));

        if (from.getBalance() < amount) {
            throw new RuntimeException("잔액 부족");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);


        // 로그 저장
        User fromUser = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("보낸 사용자 없음")).getOwner();
        User toUser = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("받은 사용자 없음")).getOwner();

        TransferLog log = TransferLog.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .transferBalance(amount)
                .fromAccountBalance(from.getBalance())
                .build();

        transferLogRepository.save(log);


    }
}
