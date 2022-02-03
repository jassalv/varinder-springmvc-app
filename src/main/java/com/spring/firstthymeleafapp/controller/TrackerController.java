package com.spring.firstthymeleafapp.controller;

import com.spring.firstthymeleafapp.dto.TransactionMapper;
import com.spring.firstthymeleafapp.model.TransactionResource;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.service.Calculator;
import com.spring.firstthymeleafapp.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class TrackerController {

    private static final String TOTAL_BALANCE = "totalbalance";
    private static final String TOTAL_INCOME = "totalincome";
    private static final String TOTAL_EXPENSE = "totalexpenses";
    private static final String INCOME_TRACKER_LIST = "incometrackerlist";
    private static final String TRANSACTION = "transaction";
    private static final String RE_DIRECT = "redirect:/home";

    Logger logger = LoggerFactory.getLogger(TrackerController.class);


    @Autowired
    TransactionService transactionService;
    @Autowired
    Calculator calculator;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute(TOTAL_BALANCE, calculator.totalBalance());
        model.addAttribute(TOTAL_INCOME, calculator.totalIncome());
        model.addAttribute(TOTAL_EXPENSE, calculator.totalExpense());
        model.addAttribute(INCOME_TRACKER_LIST, transactionService.listOfIncomeTransaction()
                .stream().map(TransactionMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute(TRANSACTION, new TransactionResource());
        return "home";
    }

    @PostMapping("/home/add")
    public String postTransaction(@Valid @ModelAttribute("transaction")TransactionResource transaction, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute(TOTAL_BALANCE, calculator.totalBalance());
            model.addAttribute(TOTAL_INCOME, calculator.totalIncome());
            model.addAttribute(TOTAL_EXPENSE, calculator.totalExpense());
            model.addAttribute(INCOME_TRACKER_LIST, transactionService.listOfIncomeTransaction()
                    .stream().map(TransactionMapper.INSTANCE::toDto)
                    .collect(Collectors.toList()));
            return "home";
        }
        if (transaction.getId() != null) {
            transactionService.updateTransaction(TransactionMapper.INSTANCE.toEntity(transaction));
            logger.info("Updating results");
        } else {
            if (transaction.getAmount() > 0) {
                transaction.setTransactionType(TransactionType.INCOME);
            } else {
                transaction.setTransactionType(TransactionType.EXPENSE);
            }
            transactionService.addTransaction(TransactionMapper.INSTANCE.toEntity(transaction));
        }
        return RE_DIRECT;
    }

    @GetMapping("/home/delete/{id}")
    public String deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return RE_DIRECT;
    }

    @GetMapping("/home/view/{id}")
    public String viewTransaction(@PathVariable Integer id, Model model) {
        model.addAttribute(TRANSACTION, TransactionMapper.INSTANCE
                .toDto(transactionService.findTransactionById(id)));
        model.addAttribute("type", "Income");
        return "view";
    }

    @GetMapping("/home/edit/{id}")
    public String editTransaction(@PathVariable Integer id, Model model) {
        model.addAttribute(TOTAL_BALANCE, calculator.totalBalance());
        model.addAttribute(TOTAL_INCOME, calculator.totalIncome());
        model.addAttribute(TOTAL_EXPENSE, calculator.totalExpense());
        model.addAttribute(INCOME_TRACKER_LIST, transactionService.listOfIncomeTransaction()
                .stream()
                .map(TransactionMapper.INSTANCE::toDto)
                .collect(Collectors.toList()));
        model.addAttribute(TRANSACTION, transactionService.findTransactionById(id));
        return "home";
    }

}

