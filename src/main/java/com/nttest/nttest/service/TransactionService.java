package com.nttest.nttest.service;

import com.nttest.nttest.dto.TransactionRequest;
import com.nttest.nttest.dto.TransactionResponse;
import com.nttest.nttest.model.Customer;
import com.nttest.nttest.model.Transaction;
import com.nttest.nttest.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionService {

    private CustomerService customerService;
    private TransactionRepository transactionRepository;

    public TransactionService(CustomerService customerService, TransactionRepository transactionRepository) {
        this.customerService = customerService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction scheduleTransaction(TransactionRequest transactionRequest) {
        double tax = calculateTax(transactionRequest.getAmount(), transactionRequest.getScheduledDate(), transactionRequest.getTransferDate());
        Customer sender = new Customer();
        Customer receiver = new Customer();
        if (transactionRequest.getSender() != transactionRequest.getReceiver()) {
            sender = this.customerService.findByUsername(transactionRequest.getSender());
            receiver = this.customerService.findByUsername(transactionRequest.getReceiver());
        }

        if (sender == null || receiver == null || sender.getId() == receiver.getId()) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setScheduledDate(transactionRequest.getScheduledDate());
        transaction.setTransferDate(transactionRequest.getTransferDate());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTaxAmount(tax);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, TransactionRequest transactionRequest) {
        if (transactionRepository.existsById(id)) {
            return scheduleTransaction(transactionRequest);
        }
        return null;
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public TransactionResponse getTransactionById(Long id) {
        return convertToResponse(transactionRepository.findById(id).orElse(null));
    }

    public List<TransactionResponse> getAllTransactions() {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> transactionResponses.add(convertToResponse(transaction)));
        return transactionResponses;
    }

    private double calculateTax(double value, LocalDate scheduleDate, LocalDate transactionDate) {
        long differenceInDays = ChronoUnit.DAYS.between(scheduleDate, transactionDate);

        if (value <= 1000) {
            if (differenceInDays == 0) {
                return value * 0.03 + 3;
            } else if (differenceInDays <= 10) {
                return value * 0.03;
            }
        } else if (value <= 2000) {
            if (differenceInDays <= 20) {
                return value * 0.082;
            } else if (differenceInDays <= 30) {
                return value * 0.069;
            } else if (differenceInDays <= 40) {
                return value * 0.047;
            } else if (differenceInDays > 40) {
                return value * 0.017;
            }
        }
        return 0;
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setScheduledDate(transaction.getScheduledDate());
        transactionResponse.setTransferDate(transaction.getTransferDate());
        transactionResponse.setSender(transaction.getSender().getUsername());
        transactionResponse.setReceiver(transaction.getReceiver().getUsername());
        transactionResponse.setTaxAmount(transaction.getTaxAmount());
        return transactionResponse;

    }

}
