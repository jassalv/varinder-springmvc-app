package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.repository.CalculatorRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Calculator {

    @Autowired
    CalculatorRepositoryImp calculatorRepositoryImp;

    public Double totalBalance() {
        Double total = calculatorRepositoryImp.totalBalance();

        if(total==null){
            return 0.0;
        }
        return total;
    }

    public Double totalIncome() {
        Double aDouble = calculatorRepositoryImp.incomeTotal();
        if(aDouble==null){
            return 0.0;
        }
        return aDouble;
    }

    public Double totalExpense() {
        Double aDouble = calculatorRepositoryImp.expenseTotal();
        if(aDouble==null){
            return 0.0;
        }
        return aDouble;
    }

    public String currentDate() {
        Date date = new Date();
        SimpleDateFormat ft =
                new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        return ft.format(date);
    }
}