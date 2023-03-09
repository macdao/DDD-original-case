package com.ddd.original.interestcalculator.v0.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Asset {
    private Long id;
    private InterestType interestType;
    private BigDecimal amount;
    private double rate;
    private int freq;
    private int dateOffset;
    private LocalDate accountingPeriodStartDate;
    private BigDecimal interestDueAmount;
    private LocalDate dayOfMouthDue;
    private BigDecimal feeDueAmount;
}
