package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.Domain.Transaction;
import com.spring.firstthymeleafapp.Domain.IncomeTracker;
import com.spring.firstthymeleafapp.abstractclasses.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeTrackerService implements Operations {

    @Autowired
    IncomeTracker incomeTracker;

    @Override
    public void deleteTransaction(Integer id) {
        Transaction transaction = incomeTracker.getTransaction().stream().filter(p->p.getId().equals(id)).findFirst().get();
        System.out.println(transaction.getAmount());
        incomeTracker.removeIncome(transaction.getAmount());
        incomeTracker.getTransaction().remove(transaction);
    }

    @Override
    public Double total() {
        return incomeTracker.getTransaction().stream().mapToDouble(Transaction::getAmount).sum();
    }
}
