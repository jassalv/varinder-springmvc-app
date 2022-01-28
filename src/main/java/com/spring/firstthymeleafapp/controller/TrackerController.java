package com.spring.firstthymeleafapp.controller;

import com.spring.firstthymeleafapp.model.TransactionE;
import com.spring.firstthymeleafapp.service.IncomeTransactionService;
import com.spring.firstthymeleafapp.service.MoneySpentTransactionService;
import com.spring.firstthymeleafapp.service.TotalAmountCalculatorService;
import com.spring.firstthymeleafapp.validator.BusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TrackerController {

    private static final String TOTAL_BALANCE = "totalbalance";
    private static final String TOTAL_INCOME = "totalincome";
    private static final String TOTAL_EXPENSE = "totalexpenses";
    private static final String INCOME_TRACKER_LIST = "incometrackerlist";
    private static final String SPENT_LIST = "spentList";
    private static final String TRANSACTION = "transaction";
    private static final String RE_DIRECT = "redirect:/home";


    @Autowired
    IncomeTransactionService incomeTransactionService;
    @Autowired
    MoneySpentTransactionService moneySpentTransactionService;
    @Autowired
    TotalAmountCalculatorService totalAmountCalculatorService;
    Integer id = 0;
    @Autowired
    BusinessValidator businessValidator;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute(TOTAL_BALANCE, totalAmountCalculatorService.calculateTotalBalance());
        model.addAttribute(TOTAL_INCOME, incomeTransactionService.total());
        model.addAttribute(TOTAL_EXPENSE, moneySpentTransactionService.total());
        model.addAttribute(INCOME_TRACKER_LIST, incomeTransactionService.listOfIncomeTransaction());
        model.addAttribute(SPENT_LIST, moneySpentTransactionService.listOfIncomeTransaction());
        model.addAttribute(TRANSACTION, new TransactionE());
        return "home";
    }

    @PostMapping("/home/adding")
    public String postItems(@ModelAttribute("expense") TransactionE transaction, Model model) {
        if (businessValidator.checkIfAlreadyExist(transaction)) {
            return RE_DIRECT;
        }
        if (businessValidator.isIncomeTransaction(transaction)) {
            incomeTransactionService.addTransaction(transaction);
        } else {
            moneySpentTransactionService.addTransaction(transaction);
        }
        return RE_DIRECT;
    }

    @GetMapping("/home/delete/income/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        incomeTransactionService.deleteTransaction(id);
        return RE_DIRECT;
    }

    @GetMapping("/home/delete/expense/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        moneySpentTransactionService.deleteTransaction(id);
        return RE_DIRECT;
    }

    @GetMapping("/home/edit/income/{id}")
    public String editIncome(@PathVariable Integer id, Model model) {
        model.addAttribute(TOTAL_BALANCE, totalAmountCalculatorService.calculateTotalBalance());
        model.addAttribute(TOTAL_INCOME, incomeTransactionService.total());
        model.addAttribute(TOTAL_EXPENSE, moneySpentTransactionService.total());
        model.addAttribute(INCOME_TRACKER_LIST, incomeTransactionService.listOfIncomeTransaction());
        model.addAttribute(SPENT_LIST, moneySpentTransactionService.listOfIncomeTransaction());
        model.addAttribute(TRANSACTION, incomeTransactionService.findTransactionById(id));
        return "home";
    }

    @GetMapping("/home/edit/expense/{id}")
    public String editExpense(@PathVariable Integer id, Model model) {
        model.addAttribute(TOTAL_BALANCE, totalAmountCalculatorService.calculateTotalBalance());
        model.addAttribute(TOTAL_INCOME, incomeTransactionService.total());
        model.addAttribute(TOTAL_EXPENSE, moneySpentTransactionService.total());
        model.addAttribute(INCOME_TRACKER_LIST, incomeTransactionService.listOfIncomeTransaction());
        model.addAttribute(SPENT_LIST, moneySpentTransactionService.listOfIncomeTransaction());
        model.addAttribute(TRANSACTION, incomeTransactionService.findTransactionById(id));
        return "home";
    }
}

