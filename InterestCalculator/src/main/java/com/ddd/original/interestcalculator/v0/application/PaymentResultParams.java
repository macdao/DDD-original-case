package com.ddd.original.interestcalculator.v0.application;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResultParams {
    private Long assetId;
    private PaymentType paymentType;
    private BigDecimal amount;
    private LocalDate paymentDate;
}
