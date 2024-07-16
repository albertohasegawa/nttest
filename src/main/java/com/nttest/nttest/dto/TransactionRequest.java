package com.nttest.nttest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class TransactionRequest {

    private double amount;
    private LocalDate scheduledDate;
    private LocalDate transferDate;
    private String sender;
    private String receiver;

}
