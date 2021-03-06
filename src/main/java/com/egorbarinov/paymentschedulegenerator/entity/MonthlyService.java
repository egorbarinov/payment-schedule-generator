package com.egorbarinov.paymentschedulegenerator.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyService {

    private Integer countOfPay;
    private LocalDate dateOfPayment;
    private BigDecimal monthlyPayment;
    private BigDecimal repaymentOfPrincipalDebtPerMonth;
    private BigDecimal percentagesPerMonth;
    private BigDecimal balanceOfDebt;

    public Integer getCountOfPay() {
        return countOfPay;
    }

    public void setCountOfPay(Integer countOfPay) {
        this.countOfPay = countOfPay;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getRepaymentOfPrincipalDebtPerMonth() {
        return repaymentOfPrincipalDebtPerMonth;
    }

    public void setRepaymentOfPrincipalDebtPerMonth(BigDecimal repaymentOfPrincipalDebtPerMonth) {
        this.repaymentOfPrincipalDebtPerMonth = repaymentOfPrincipalDebtPerMonth;
    }

    public BigDecimal getPercentagesPerMonth() {
        return percentagesPerMonth;
    }

    public void setPercentagesPerMonth(BigDecimal percentagesPerMonth) {
        this.percentagesPerMonth = percentagesPerMonth;
    }

    public BigDecimal getBalanceOfDebt() {
        return balanceOfDebt;
    }

    public void setBalanceOfDebt(BigDecimal balanceOfDebt) {
        this.balanceOfDebt = balanceOfDebt;
    }

    @Override
    public String toString() {
        return "MonthlyService{" +
                "countOfPay=" + countOfPay +
                ", dateOfPayment=" + dateOfPayment +
                ", monthlyPayment=" + monthlyPayment +
                ", repaymentOfPrincipalDebtPerMonth=" + repaymentOfPrincipalDebtPerMonth +
                ", percentagesPerMonth=" + percentagesPerMonth +
                ", balanceOfDebt=" + balanceOfDebt +
                '}';
    }
}
