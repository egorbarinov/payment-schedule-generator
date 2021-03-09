package com.egorbarinov.paymentschedulegenerator.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public List<MonthlyPayment> getMonthlyPaymentList() {
        return monthlyPaymentList;
    }

    public void setMonthlyPaymentList(List<MonthlyPayment> monthlyPaymentList) {
        this.monthlyPaymentList = monthlyPaymentList;
    }

    public LocalDate getDateOfIssueOfLoan() {
        return dateOfIssueOfLoan;
    }

    public void setDateOfIssueOfLoan(LocalDate dateOfIssueOfLoan) {
        this.dateOfIssueOfLoan = dateOfIssueOfLoan;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getBalanceOfDebt() {
        return balanceOfDebt;
    }

    public void setBalanceOfDebt(BigDecimal balanceOfDebt) {
        this.balanceOfDebt = balanceOfDebt;
    }

    public BigDecimal getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(BigDecimal percentRate) {
        this.percentRate = percentRate;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

}
