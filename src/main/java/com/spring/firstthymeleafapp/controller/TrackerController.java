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
    String getHomePage(Model model) {
            model.addAttribute("totalbalance", totalAmountCalculatorService.calculateTotalBalance());
            model.addAttribute("totalincome", incomeTransactionService.total());
            model.addAttribute("totalexpenses", moneySpentTransactionService.total());
            model.addAttribute("incometrackerlist", incomeTransactionService.listOfIncomeTransaction());
            model.addAttribute("spentList", moneySpentTransactionService.listOfIncomeTransaction());
            model.addAttribute("transaction", new TransactionE());
        return "home";
    }

    @PostMapping("/home/adding")
    String postItems(@ModelAttribute("expense") TransactionE transaction, Model model) {
        if (businessValidator.checkIfAlreadyExist(transaction)) {
            return "redirect:/home";
        }
        if (businessValidator.isIncomeTransaction(transaction)) {
            incomeTransactionService.addTransaction(transaction);
        } else {
            moneySpentTransactionService.addTransaction(transaction);
        }
        return "redirect:/home";
    }


    @GetMapping("/home/delete/income/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        incomeTransactionService.deleteTransaction(id);
        return "redirect:/home";
    }

    @GetMapping("/home/delete/expense/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        moneySpentTransactionService.deleteTransaction(id);
        return "redirect:/home";
    }

    @GetMapping("/home/edit/income/{id}")
    public String editIncome(@PathVariable Integer id, Model model) {
        model.addAttribute("totalbalance", totalAmountCalculatorService.calculateTotalBalance());
        model.addAttribute("totalincome", incomeTransactionService.total());
        model.addAttribute("totalexpenses", moneySpentTransactionService.total());
        model.addAttribute("incometrackerlist", incomeTransactionService.listOfIncomeTransaction());
        model.addAttribute("spentList", moneySpentTransactionService.listOfIncomeTransaction());
        model.addAttribute("transaction", incomeTransactionService.findTransactionById(id));
        return "home";
    }

    @GetMapping("/home/edit/expense/{id}")
    public String editExpense(@PathVariable Integer id, Model model) {
        model.addAttribute("totalbalance", totalAmountCalculatorService.calculateTotalBalance());
        model.addAttribute("totalincome", incomeTransactionService.total());
        model.addAttribute("totalexpenses", moneySpentTransactionService.total());
        model.addAttribute("incometrackerlist", incomeTransactionService.listOfIncomeTransaction());
        model.addAttribute("spentList", moneySpentTransactionService.listOfIncomeTransaction());
        model.addAttribute("transaction", moneySpentTransactionService.findTransactionById(id));
        return "home";
    }
}
