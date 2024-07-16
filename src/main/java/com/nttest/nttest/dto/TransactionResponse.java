package com.nttest.nttest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class TransactionResponse {

    private double amount;
    private LocalDate scheduledDate;
    private LocalDate transferDate;
    private double taxAmount;
    private String sender;
    private String receiver;

}
