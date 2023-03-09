package com.ddd.original.interestcalculator.v0.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeePaymentHistory {
    private Long assetId;
    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
}
