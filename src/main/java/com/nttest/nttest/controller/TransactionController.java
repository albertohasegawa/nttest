package com.nttest.nttest.controller;

import com.nttest.nttest.dto.TransactionRequest;
import com.nttest.nttest.dto.TransactionResponse;
import com.nttest.nttest.model.Transaction;
import com.nttest.nttest.service.TransactionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> scheduleTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            Transaction transaction = transactionService.scheduleTransaction(transactionRequest);
            if (transaction == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing transaction");
            }
            return ResponseEntity.ok("Transaction scheduled.");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error processing transaction");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        try {
            Transaction transaction = transactionService.updateTransaction(id, transactionRequest);
            if (transaction == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing transaction");
            }
            return ResponseEntity.ok("Transaction updated.");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error processing transaction");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        ;
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

}
