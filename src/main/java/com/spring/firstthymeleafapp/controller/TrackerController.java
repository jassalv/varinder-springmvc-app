package com.spring.firstthymeleafapp.controller;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.model.TransactionSummary;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.service.TotalAmountCalculatorService;
import com.spring.firstthymeleafapp.service.TransactionService;
import com.spring.firstthymeleafapp.validator.BusinessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(TrackerController.class);


    @Autowired
    TransactionService transactionService;
    @Autowired
    TotalAmountCalculatorService totalAmountCalculatorService;
    Integer id = 0;
    @Autowired
    BusinessValidator businessValidator;
    @Autowired
    TransactionSummary transactionSummary;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute(TOTAL_BALANCE, transactionService.findSum().getBalance());
        model.addAttribute(TOTAL_INCOME, transactionService.findSum().getIncome());
        model.addAttribute(TOTAL_EXPENSE, transactionService.findSum().getExpense());
        model.addAttribute(INCOME_TRACKER_LIST, transactionService.listOfIncomeTransaction());
        model.addAttribute(TRANSACTION, new TransactionEResource());
        return "home";
    }

    @PostMapping("/home/adding")
    public String postItems(@ModelAttribute("expense") TransactionEResource transaction, Model model) {
        if (transaction.getId() !=null) {
            transactionService.updateTransaction(transaction);
            logger.info("Updating results");
        } else {
            if(transaction.getAmount()>0){
                transaction.setTransactionType(TransactionType.INCOME);
            }else{
                transaction.setTransactionType(TransactionType.EXPENSE);
            }
                transactionService.addTransaction(transaction);
        }
        return RE_DIRECT;
    }

    @GetMapping("/home/delete/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return RE_DIRECT;
    }

    @GetMapping("/home/view/{id}")
    public String viewIncomeTransaction(@PathVariable Integer id, Model model) {
        model.addAttribute(TRANSACTION, transactionService.findTransactionById(id));
        model.addAttribute("type", "Income");
        return "view";
    }

    @GetMapping("/home/edit/income/{id}")
    public String editIncome(@PathVariable Integer id, Model model) {
        model.addAttribute(TOTAL_BALANCE, transactionService.findSum().getBalance());
        model.addAttribute(TOTAL_INCOME, transactionService.findSum().getIncome());
        model.addAttribute(TOTAL_EXPENSE, transactionService.findSum().getExpense());
        model.addAttribute(INCOME_TRACKER_LIST, transactionService.listOfIncomeTransaction());
        model.addAttribute(TRANSACTION, new TransactionEResource());
        return "home";
    }

}

