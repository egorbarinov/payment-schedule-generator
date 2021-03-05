package com.egorbarinov.paymentschedulegenerator.service;

import com.egorbarinov.paymentschedulegenerator.entity.Loan;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private static final List<Loan> list =new ArrayList<>();
    private static Integer numberOfPayments = 0;
    private Loan loan;

    public static int loanCalculator (LocalDate dateOfIssueOfLoan, LocalDate date, BigDecimal balanceOfDebt, BigDecimal monthlyPayment, BigDecimal percentRate, Integer period) {

        if (numberOfPayments.equals(period)) return 0;
        // инициалиируем кредит
        Loan loan = new Loan();
        loan.setDateOfIssueOfLoan(dateOfIssueOfLoan); // устанавливаем дату выдачи кредита
        loan.setCompletionDate(dateOfIssueOfLoan.plusMonths(120));
        loan.setBalanceOfDebt(balanceOfDebt);
        loan.setPercentRate(percentRate); // устанавливаем процентную ставку
        loan.setMonthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP)); // устанавливаем ежемесячный платеж
        loan.setPeriodOfPayments(++numberOfPayments); // устанавливаем будущий период оплаты по кредиту
        loan.setDateOfPayment(date.plusMonths(1));// устанавливаем будущую дату оплаты по кредиту

        long deltaDates = ChronoUnit.DAYS.between(date, loan.getDateOfPayment());
        int countDaysOfYear = ((loan.getDateOfPayment().getYear() % 4 == 0) && (loan.getDateOfPayment().getYear() % 100 != 0) || (loan.getDateOfPayment().getYear() % 400 == 0) ? 366 : 365);

        // считаем начисленные проценты  =СУММ(ОСТАТОК ЗАДОЛЖЕННОСТИ *(КОЛ-ВО ДНЕЙ В МЕСЯЦЕ)/365*ПЦ(В ФОРМАТЕ 0,112)
        BigDecimal percentagesPerMonth;
        Month month = loan.getDateOfPayment().getMonth();
        if (month.equals(Month.JANUARY)) {
            countDaysOfYear = ((loan.getDateOfPayment().minusMonths(1).getYear() % 4 == 0) && (loan.getDateOfPayment().minusMonths(1).getYear() % 100 != 0) || (loan.getDateOfPayment().minusMonths(1).getYear() % 400 == 0) ? 366 : 365);
            BigDecimal percentagesAsOfDecember = chargeInterest(balanceOfDebt, 4, countDaysOfYear, percentRate);
            countDaysOfYear = ((loan.getDateOfPayment().getYear() % 4 == 0) && (loan.getDateOfPayment().getYear() % 100 != 0) || (loan.getDateOfPayment().getYear() % 400 == 0) ? 366 : 365);
            BigDecimal percentagesAsOfJanuaryOfNewYear = chargeInterest(balanceOfDebt, 27, countDaysOfYear, percentRate);
            percentagesPerMonth = percentagesAsOfDecember.add(percentagesAsOfJanuaryOfNewYear).setScale(2, RoundingMode.HALF_UP);
        }
        else {
            percentagesPerMonth = chargeInterest(balanceOfDebt, deltaDates, countDaysOfYear, percentRate).setScale(2, RoundingMode.HALF_UP);
        }
        loan.setPercentagesPerMonth(percentagesPerMonth.setScale(2, RoundingMode.HALF_UP));

        BigDecimal newBalanceOfDebt;

        if (!loan.getDateOfPayment().equals(loan.getCompletionDate())) {
            BigDecimal repaymentOfPrincipalDebtPerMonth = monthlyPayment.subtract(percentagesPerMonth).setScale(2, RoundingMode.HALF_UP);
            loan.setRepaymentOfPrincipalDebtPerMonth(repaymentOfPrincipalDebtPerMonth);
            newBalanceOfDebt = balanceOfDebt.subtract(repaymentOfPrincipalDebtPerMonth).setScale(2, RoundingMode.HALF_UP);
            loan.setBalanceOfDebt(newBalanceOfDebt);

        } else {
            if (monthlyPayment.compareTo(loan.getBalanceOfDebt()) >= 0) {
                monthlyPayment = loan.getBalanceOfDebt().add(percentagesPerMonth);
                loan.setMonthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            }
            BigDecimal repaymentOfPrincipalDebtPerMonth = monthlyPayment.subtract(percentagesPerMonth);
            loan.setRepaymentOfPrincipalDebtPerMonth(repaymentOfPrincipalDebtPerMonth.setScale(2, RoundingMode.HALF_UP));
            newBalanceOfDebt = BigDecimal.ZERO;
            loan.setBalanceOfDebt(newBalanceOfDebt.setScale(2, RoundingMode.HALF_UP));
        }
        list.add(loan);
        return loanCalculator(LocalDate.of(2017,12,27), date.plusMonths(1), newBalanceOfDebt, monthlyPayment, percentRate, period);
    }

    //рассчет ежемесячного платежа =СУММ(СУММА_КРЕДИТА*(0,112/12)/(1-1/(1+0,112/12)^(СРОК_КРЕДИТА)))
    public static BigDecimal calculateMonthlyPayment(BigDecimal balanceOfDebt, BigDecimal percentRate, Integer period) {

        BigDecimal percentToMonth = percentRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        return balanceOfDebt.multiply(percentToMonth)
                .divide(BigDecimal.ONE.subtract((BigDecimal.ONE)
                        .divide(BigDecimal.ONE
                                .add(percentToMonth)
                                .pow(period), MathContext.DECIMAL128)), MathContext.DECIMAL128);
    }

    // расчет начисленных процентов
    private static BigDecimal chargeInterest(BigDecimal balanceOfDebt, long deltaDates, int countDaysOfYear, BigDecimal percentRate) {
        return balanceOfDebt.multiply(BigDecimal.valueOf(deltaDates)
                .divide(BigDecimal.valueOf(countDaysOfYear), MathContext.DECIMAL128))
                .multiply(percentRate);
    }

    public static void main(String[] args) {
        BigDecimal sum = calculateMonthlyPayment(BigDecimal.valueOf(3_330_802.20), BigDecimal.valueOf(0.112),120);
        loanCalculator(LocalDate.of(2017,12,27), LocalDate.of(2017,12,27), BigDecimal.valueOf(3_330_802.20), sum, BigDecimal.valueOf(0.112), 120);
        list.forEach(System.out::println);

    }
}
