package com.example.concurrency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    // 받는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUser")
    private User toUser;

    // 보내는 사람
    @JoinColumn(name = "fromUser")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    // 이체 금액
    private Long transferBalance;

    // 송신자 남은 잔액
    private Long fromAccountBalance;
}
