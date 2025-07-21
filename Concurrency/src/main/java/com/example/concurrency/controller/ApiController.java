package com.example.concurrency.controller;

import com.example.concurrency.dto.TransferDTO;
import com.example.concurrency.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    private final TransferService transferService;

    @PostMapping("/transferHardLock")
    public ResponseEntity<String> transferHardLock(@RequestBody TransferDTO transferDTO) {
        try {
            transferService.transferWithPessimisticLock(
                    transferDTO.getFromId(),
                    transferDTO.getToId(),
                    transferDTO.getAmount()
            );
            log.info("Transfer Success: {}", transferDTO);
            return ResponseEntity.ok("Transfer Success");
        } catch (Exception e) {
            log.error("Transfer Failed", e);
            return ResponseEntity.badRequest().body("Transfer Failed: " + e.getMessage());
        }
    }

    @PostMapping("/transferPositiveLock")
    public ResponseEntity<String> transferPositiveLock(@RequestBody TransferDTO transferDTO) {
        try {
            transferService.transferWithPositiveLock(
                    transferDTO.getFromId(),
                    transferDTO.getToId(),
                    transferDTO.getAmount()
            );
            log.info("Transfer Success: {}", transferDTO);
            return ResponseEntity.ok("Transfer Success");
        } catch (Exception e) {
            log.error("Transfer Failed", e);
            return ResponseEntity.badRequest().body("Transfer Failed: " + e.getMessage());
        }
    }

    @PostMapping("/transferNoLock")
    public ResponseEntity<String> transferNoLock(@RequestBody TransferDTO transferDTO) {
        try {
            transferService.transferWithNoLock(
                    transferDTO.getFromId(),
                    transferDTO.getToId(),
                    transferDTO.getAmount()
            );
            log.info("Transfer Success: {}", transferDTO);
            return ResponseEntity.ok("Transfer Success");
        } catch (Exception e) {
            log.error("Transfer Failed", e);
            return ResponseEntity.badRequest().body("Transfer Failed: " + e.getMessage());
        }
    }
}
