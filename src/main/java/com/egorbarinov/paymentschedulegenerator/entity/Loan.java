package com.egorbarinov.paymentschedulegenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssueOfLoan;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;
    private BigDecimal amount;
    private BigDecimal balanceOfDebt;
    private BigDecimal percentRate;
    private BigDecimal monthlyPayment;
    private Integer creditPeriod;
    private List<MonthlyPayment> monthlyPaymentList;

    public Loan(LocalDate dateOfIssueOfLoan, LocalDate completionDate, BigDecimal balanceOfDebt, BigDecimal percentRate, BigDecimal monthlyPayment, Integer creditPeriod) {
        this.amount = balanceOfDebt;
        this.dateOfIssueOfLoan = dateOfIssueOfLoan;
        this.completionDate = completionDate;
        this.balanceOfDebt = balanceOfDebt;
        this.percentRate = percentRate;
        this.monthlyPayment = monthlyPayment;
        this.creditPeriod = creditPeriod;
    }


}
