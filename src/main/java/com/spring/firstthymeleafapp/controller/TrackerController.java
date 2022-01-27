package com.spring.firstthymeleafapp.controller;
import com.spring.firstthymeleafapp.Domain.Transaction;
import com.spring.firstthymeleafapp.Domain.IncomeTracker;
import com.spring.firstthymeleafapp.abstractclasses.Tracker;
import com.spring.firstthymeleafapp.service.IncomeTrackerService;
import com.spring.firstthymeleafapp.Domain.MoneySpendTracker;
import com.spring.firstthymeleafapp.service.MoneySpentTrackerService;
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
    IncomeTracker incomeTracker;
    @Autowired
    MoneySpendTracker moneySpendTracker;
    @Autowired
    IncomeTrackerService incomeTrackerService;
    @Autowired
    MoneySpentTrackerService moneySpentTrackerService;
    Integer id=0;
    @GetMapping("/home")
    String getHomePage(Model model){
        if(incomeTracker.getTransaction().isEmpty()) {
            model.addAttribute("totalbalance", incomeTracker.getTotalBalance());
            model.addAttribute("totalincome",incomeTrackerService.total());
            model.addAttribute("totalexpenses",moneySpentTrackerService.total());
            model.addAttribute("incometrackerlist",incomeTracker.getTransaction());
            model.addAttribute("spentList", moneySpendTracker.getTransaction());
            model.addAttribute("transaction", new Transaction());
        }else{
            model.addAttribute("totalbalance", incomeTracker.getTotalBalance());
            model.addAttribute("totalincome",incomeTrackerService.total());
            model.addAttribute("totalexpenses",moneySpentTrackerService.total());
            model.addAttribute("incometrackerlist",incomeTracker.getTransaction());
            model.addAttribute("spentList", moneySpendTracker.getTransaction());
            model.addAttribute("transaction", new Transaction());
        }
        return "home";
    }

    @PostMapping ("/home/adding")
    String postItems(@ModelAttribute("expense") Transaction transaction, Model model){
       if(transaction.getAmount()>0){
           System.out.println(incomeTracker.getTransaction());
           if(incomeTracker.getTransaction().contains(transaction)){
               System.out.println("I ham here");
             incomeTracker.getTransaction().set(incomeTracker.getTransaction().indexOf(transaction),transaction);
           }
           transaction.setId(++id);
           incomeTracker.addIncome(transaction.getAmount());
           incomeTracker.getTransaction().add(transaction);
       }else{
           transaction.setId(++id);
           moneySpendTracker.addExpense(transaction.getAmount());
           moneySpendTracker.getTransaction().add(transaction);
       }
        return "redirect:/home";
    }

    @GetMapping("/home/delete/income/{id}")
    public String deleteIncome(@PathVariable Integer id) {
        incomeTrackerService.deleteTransaction(id);
        return "redirect:/home";
    }

    @GetMapping("/home/delete/expense/{id}")
    public String deleteExpense(@PathVariable Integer id) {
        moneySpentTrackerService.deleteTransaction(id);
        return "redirect:/home";
    }

    @GetMapping("/home/edit/income/{id}")
    public String editIncome(@PathVariable Integer id, Model model) {
        model.addAttribute("totalbalance", incomeTracker.getTotalBalance());
        model.addAttribute("totalincome",incomeTrackerService.total());
        model.addAttribute("totalexpenses",moneySpentTrackerService.total());
        model.addAttribute("incometrackerlist",incomeTracker.getTransaction());
        model.addAttribute("spentList", moneySpendTracker.getTransaction());
        model.addAttribute("transaction", incomeTracker.findTransaction(id));
        return "home";
    }
    @GetMapping("/home/edit/expense/{id}")
    public String editExpense(@PathVariable Integer id, Model model) {
        model.addAttribute("totalbalance", incomeTracker.getTotalBalance());
        model.addAttribute("totalincome",incomeTrackerService.total());
        model.addAttribute("totalexpenses",moneySpentTrackerService.total());
        model.addAttribute("incometrackerlist",incomeTracker.getTransaction());
        model.addAttribute("spentList", moneySpendTracker.getTransaction());
        model.addAttribute("transaction", moneySpendTracker.findTransaction(id));
        return "home";
    }
}
