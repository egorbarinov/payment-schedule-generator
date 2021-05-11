package com.egorbarinov.paymentschedulegenerator.entity;

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
public class MonthlyLoanServicing {

    private Integer countOfPay;
    private LocalDate dateOfPayment;
    private BigDecimal monthlyPayment;
    private BigDecimal repaymentOfPrincipalDebtPerMonth;
    private BigDecimal percentagesPerMonth;
    private BigDecimal balanceOfDebt;

}
