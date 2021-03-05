package com.egorbarinov.paymentschedulegenerator.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {

    private LocalDate dateOfIssueOfLoan;
    private LocalDate completionDate;
    private BigDecimal balanceOfDebt;
    private BigDecimal percentRate;
    private Integer periodOfPayments;
    private LocalDate dateOfPayment;
    private BigDecimal percentagesPerMonth;
    private BigDecimal repaymentOfPrincipalDebtPerMonth;
    private BigDecimal monthlyPayment;


    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
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

    public Integer getPeriodOfPayments() {
        return periodOfPayments;
    }

    public void setPeriodOfPayments(Integer periodOfPayments) {
        this.periodOfPayments = periodOfPayments;
    }

    public BigDecimal getPercentagesPerMonth() {
        return percentagesPerMonth;
    }

    public void setPercentagesPerMonth(BigDecimal percentagesPerMonth) {
        this.percentagesPerMonth = percentagesPerMonth;
    }

    public BigDecimal getRepaymentOfPrincipalDebtPerMonth() {
        return repaymentOfPrincipalDebtPerMonth;
    }

    public void setRepaymentOfPrincipalDebtPerMonth(BigDecimal repaymentOfPrincipalDebtPerMonth) {
        this.repaymentOfPrincipalDebtPerMonth = repaymentOfPrincipalDebtPerMonth;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "  periodOfPayments=" + periodOfPayments +
                ", dateOfPayment=" + dateOfPayment +
                ", monthlyPayment=" + monthlyPayment +
                ", percentagesPerMonth=" + percentagesPerMonth +
                ", repaymentOfPrincipalDebtPerMonth=" + repaymentOfPrincipalDebtPerMonth +
                ", balanceOfDebt=" + balanceOfDebt +
                " " +
                '}';
    }
}
