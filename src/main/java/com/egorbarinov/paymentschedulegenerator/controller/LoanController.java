package com.egorbarinov.paymentschedulegenerator.controller;

import com.egorbarinov.paymentschedulegenerator.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class LoanController {
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }
}
