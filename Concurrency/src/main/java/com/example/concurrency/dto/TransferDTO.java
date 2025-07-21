package com.example.concurrency.dto;

import lombok.Data;

@Data
public class TransferDTO {
    // 송금인
    private Long fromId;

    // 수취인
    private Long toId;

    // 송금 금액
    private Long amount;
}
