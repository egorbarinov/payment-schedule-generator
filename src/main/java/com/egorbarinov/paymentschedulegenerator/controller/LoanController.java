package com.egorbarinov.paymentschedulegenerator.controller;

import com.egorbarinov.paymentschedulegenerator.entity.Loan;
import com.egorbarinov.paymentschedulegenerator.service.ExcelReportView;
import com.egorbarinov.paymentschedulegenerator.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.time.LocalDate;



@Controller
public class LoanController {

    private LoanService loanService;
    private Loan reportLoan;


    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping({"/doCalculate"})
    public String uploadDate(Model model, Loan loan) {
        model.addAttribute("loan", loan);
        return "doCalculate";
    }

    @PostMapping("/doCalculate")
    public String doCalculate(Loan loan, Model model) {
        if (loan.getBalanceOfDebt().equals(BigDecimal.ZERO) || loan.getPercentRate().equals(BigDecimal.ZERO) || loan.getCreditPeriod() == 0) {
            model.addAttribute("amountError", "Поле не может быть 0. Введите значение!");
            return "doCalculate";
        }
        model.addAttribute("loan", loan);

        loan = loanService.creditCalculation(
                loan.getDateOfIssueOfLoan()
                ,loan.getBalanceOfDebt()
                , loan.getPercentRate()
                , loan.getCreditPeriod());
        loanService.paymentSchedule(loan);
        reportLoan = loan;
        model.addAttribute("payments", loan.getMonthlyServiceList());
        return "doCalculate";
    }


    @RequestMapping(method= RequestMethod.GET, value = "/report")
    public ModelAndView getExcel(){

        if (reportLoan != null) {
            return new ModelAndView(new ExcelReportView(), "reportLoan", reportLoan);
        } else {
            Loan loan = loanService.creditCalculation(LocalDate.now(), new BigDecimal("1000000"), new BigDecimal("10"), 10);
            loanService.paymentSchedule(loan);
            reportLoan = loan;
            return new ModelAndView(new ExcelReportView(), "reportLoan", reportLoan);
        }
    }



}
