package com.example.concurrency.controller;

import com.example.concurrency.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@Log4j2
@RequiredArgsConstructor
public class Controller {
    private final TransferService transferService;
    @GetMapping(value = "/test")
    public String test() {
        transferService.transferWithPessimisticLock(1L, 2L, 1000L);
        log.info("Transfer Test Data !!");
        return "test";
    }


}
