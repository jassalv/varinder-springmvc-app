package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.repository.IncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalAmountCalculatorService implements Balance {

    @Autowired
    IncomeDao incomeDao;

    @Override
    public Double calculateTotalBalance() {
        return incomeDao.fillAll().stream().mapToDouble(TransactionEResource::getAmount).sum();
    }
}
