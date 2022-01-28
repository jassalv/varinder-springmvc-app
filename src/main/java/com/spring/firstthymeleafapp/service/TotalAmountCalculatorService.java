package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.TransactionE;
import com.spring.firstthymeleafapp.abstractclasses.Balance;
import com.spring.firstthymeleafapp.repository.IncomeDao;
import com.spring.firstthymeleafapp.repository.MoneySpentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalAmountCalculatorService implements Balance {

    @Autowired
    IncomeDao incomeDao;
    @Autowired
    MoneySpentDao moneySpentDao;

    @Override
    public Double calculateTotalBalance() {
        Double totalIncome = incomeDao.fillAll().stream().mapToDouble(TransactionE::getAmount).sum();
        Double totalExpenses = moneySpentDao.fillAll().stream().mapToDouble(TransactionE::getAmount).sum();
        return totalIncome + totalExpenses;
    }
}
