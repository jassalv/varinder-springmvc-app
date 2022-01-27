package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.Domain.Transaction;
import com.spring.firstthymeleafapp.Domain.MoneySpendTracker;
import com.spring.firstthymeleafapp.abstractclasses.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneySpentTrackerService implements Operations {

    @Autowired
    MoneySpendTracker moneySpendTracker;

    @Override
    public void deleteTransaction(Integer id) {
        Transaction transaction = moneySpendTracker.getTransaction().stream().filter(p->p.getId().equals(id)).findFirst().get();
        System.out.println(transaction.getAmount());
        moneySpendTracker.removeExpense(Math.abs(transaction.getAmount()));
        moneySpendTracker.getTransaction().remove(transaction);
    }

    @Override
    public Double total() {
        return moneySpendTracker.getTransaction().stream().mapToDouble(Transaction::getAmount).sum();
    }
}
