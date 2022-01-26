package com.spring.firstthymeleafapp.controller;

import com.spring.firstthymeleafapp.ExpenseTracker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TrackerController {

    List<ExpenseTracker> expenseTrackerList = new ArrayList<>();
    ExpenseTracker expenseTracker2 = new ExpenseTracker();
    Double income =0.0;
    Double expenses=0.0;
    Integer id=0;

    @GetMapping("/home")
    String getHomePage(Model model){
        if(expenseTrackerList.isEmpty()) {
            model.addAttribute("expense",new ExpenseTracker());
            model.addAttribute("expenses",new ArrayList<>());
            model.addAttribute("expense1", new ExpenseTracker());
        }else{

            expenseTracker2.setAmount(expenseTrackerList.stream().mapToDouble(ExpenseTracker::getAmount).sum());
            ExpenseTracker expenseTracker =new ExpenseTracker();
            model.addAttribute("expense", expenseTracker2);
            model.addAttribute("expenses", expenseTrackerList);
            model.addAttribute("expense1", expenseTracker);
        }
        System.out.println(expenseTrackerList.toString());
        return "home";
    }

    @PostMapping ("/home/adding")
    String postItems(@ModelAttribute("expense") ExpenseTracker expenseTracker, Model model){
       if(expenseTracker.getAmount()>0){
           expenseTracker.setId(++id);
           income+= expenseTracker.getAmount();
       }else{
           expenseTracker.setId(++id);
           expenses+= expenseTracker.getAmount();
       }
       expenseTrackerList.add(expenseTracker);
        return "redirect:/home";
    }

    @GetMapping("/home/delete/{id}")
    public String deleteWidget(@PathVariable Integer id) {
        ExpenseTracker first = expenseTrackerList.stream().filter(p -> p.getId().equals(id)).findFirst().get();
        expenseTrackerList.remove(first);
        return "redirect:/home";
    }
    @GetMapping("/home/edit/{id}")
    public String editWidget(@PathVariable Integer id, Model model) {
        expenseTracker2.setAmount(expenseTrackerList.stream().mapToDouble(ExpenseTracker::getAmount).sum());
        model.addAttribute("expense", expenseTracker2);
        model.addAttribute("expense1", expenseTrackerList.stream().filter(p->p.getId().equals(id)).findFirst().get());
        model.addAttribute("expenses", expenseTrackerList);
        return "home";
    }
}
