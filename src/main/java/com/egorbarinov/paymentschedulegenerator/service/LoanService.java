package com.egorbarinov.paymentschedulegenerator.service;

import com.egorbarinov.paymentschedulegenerator.entity.MonthlyPayment;
import com.egorbarinov.paymentschedulegenerator.entity.Loan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = "prototype", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class LoanService {

    private List<MonthlyPayment> list;

    public LoanService() {
        this.list = new ArrayList<>();
    }

    private Integer numberOfPayments = 0;

    //рассчитать дату последнего платежного периода, рассчитать ежемесячный платежб создать кредит
    public Loan creditCalculation(LocalDate dateOfIssueOfLoan, BigDecimal amount, BigDecimal interest, Integer creditPeriod) {
        LocalDate completionDate = dateOfIssueOfLoan.plusMonths(creditPeriod);
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, interest, creditPeriod);
        return new Loan(dateOfIssueOfLoan,completionDate,amount,interest,monthlyPayment,creditPeriod);
    }

    // создать аннуитетный график платежей
    public void paymentSchedule(Loan loan) {
        list.clear();
        while (!numberOfPayments.equals(loan.getCreditPeriod())) {
            var payment = new MonthlyPayment();
            payment.setBalanceOfDebt(loan.getBalanceOfDebt());
            payment.setMonthlyPayment(loan.getMonthlyPayment().setScale(2, RoundingMode.HALF_UP));
            long deltaDates = ChronoUnit.DAYS.between(loan.getDateOfIssueOfLoan()
                    .plusMonths(numberOfPayments), loan.getDateOfIssueOfLoan()
                    .plusMonths(++numberOfPayments));
            payment.setDateOfPayment(loan.getDateOfIssueOfLoan().plusMonths(numberOfPayments));
            payment.setCountOfPay(numberOfPayments);

            int countDaysOfYear = (getNumberOfDaysInYear(payment.getDateOfPayment()));
            BigDecimal percentagesPerMonth;

            Month month = payment.getDateOfPayment().getMonth();
            if (month.equals(Month.JANUARY)){
                countDaysOfYear = getNumberOfDaysInYear(payment.getDateOfPayment().minusMonths(1));
                BigDecimal percentagesAsOfDecember = chargeInterest(loan.getBalanceOfDebt(), (Month.DECEMBER.maxLength() - payment.getDateOfPayment().minusMonths(1).getDayOfMonth()), countDaysOfYear, loan.getPercentRate());
                countDaysOfYear = getNumberOfDaysInYear(payment.getDateOfPayment());
                BigDecimal percentagesAsOfJanuaryOfNewYear = chargeInterest(loan.getBalanceOfDebt(), payment.getDateOfPayment().getDayOfMonth(), countDaysOfYear, loan.getPercentRate());
                percentagesPerMonth = percentagesAsOfDecember.add(percentagesAsOfJanuaryOfNewYear).setScale(2, RoundingMode.HALF_UP);
            } else {
                percentagesPerMonth = chargeInterest(loan.getBalanceOfDebt(), deltaDates, countDaysOfYear, loan.getPercentRate()).setScale(2, RoundingMode.HALF_UP);
            }
            payment.setPercentagesPerMonth(percentagesPerMonth.setScale(2, RoundingMode.HALF_UP));
            payment.setBalanceOfDebt(getNewBalance(loan, payment));
            list.add(payment);
        }
        loan.setMonthlyPaymentList(list);

    }

    private int getNumberOfDaysInYear(LocalDate dateOfPayment) {
        return (dateOfPayment.getYear() % 4 == 0) && (dateOfPayment.getYear() % 100 != 0) || (dateOfPayment.getYear() % 400 == 0) ? 366 : 365;
    }

    // расчет остатка задолженности по аннуитетному кредиту
    private BigDecimal getNewBalance(Loan loan, MonthlyPayment payment) {
        BigDecimal newBalanceOfDebt;
        if (!payment.getDateOfPayment().equals(loan.getCompletionDate())) {
            BigDecimal repaymentOfPrincipalDebtPerMonth = loan.getMonthlyPayment().subtract(payment.getPercentagesPerMonth()).setScale(2, RoundingMode.HALF_UP);
            payment.setRepaymentOfPrincipalDebtPerMonth(repaymentOfPrincipalDebtPerMonth);
            newBalanceOfDebt = loan.getBalanceOfDebt().subtract(repaymentOfPrincipalDebtPerMonth).setScale(2, RoundingMode.HALF_UP);
            loan.setBalanceOfDebt(newBalanceOfDebt);

        } else {
            if (payment.getMonthlyPayment().compareTo(loan.getBalanceOfDebt()) >= 0) {
                payment.setMonthlyPayment(loan.getBalanceOfDebt().add(payment.getPercentagesPerMonth()).setScale(2, RoundingMode.HALF_UP));
            }
            BigDecimal repaymentOfPrincipalDebtPerMonth = payment.getMonthlyPayment().subtract(payment.getPercentagesPerMonth());
            payment.setRepaymentOfPrincipalDebtPerMonth(repaymentOfPrincipalDebtPerMonth.setScale(2, RoundingMode.HALF_UP));
            newBalanceOfDebt = BigDecimal.ZERO;
            loan.setBalanceOfDebt(newBalanceOfDebt.setScale(2, RoundingMode.HALF_UP));
        }
        return newBalanceOfDebt;
    }

    //рассчет аннуитетного ежемесячного платежа =СУММ(СУММА_КРЕДИТА*(0,112/12)/(1-1/(1+0,112/12)^(СРОК_КРЕДИТА)))
    private BigDecimal calculateMonthlyPayment(BigDecimal balanceOfDebt, BigDecimal percentRate, Integer period) {
        System.out.println();
        BigDecimal percentToMonth = percentRate.divide(BigDecimal.valueOf(100), 3, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        return balanceOfDebt.multiply(percentToMonth)
                .divide(BigDecimal.ONE.subtract((BigDecimal.ONE)
                        .divide(BigDecimal.ONE
                                .add(percentToMonth)
                                .pow(period), MathContext.DECIMAL128)), MathContext.DECIMAL128);
    }

    // расчет начисленных процентов по аннуитетному кредиту
    private BigDecimal chargeInterest(BigDecimal balanceOfDebt, long deltaDates, int countDaysOfYear, BigDecimal percentRate) {
        return balanceOfDebt.multiply(BigDecimal.valueOf(deltaDates)
                .divide(BigDecimal.valueOf(countDaysOfYear), MathContext.DECIMAL128))
                .multiply(percentRate.divide(BigDecimal.valueOf(100), 3, RoundingMode.HALF_UP));
    }

    public static void main(String[] args) {
        LoanService service = new LoanService();
        Loan loan = service.creditCalculation(LocalDate.of(2017,12,27), new BigDecimal("3330802.20"), new BigDecimal("11.2"), 120);
        service.paymentSchedule(loan);

    }

}
