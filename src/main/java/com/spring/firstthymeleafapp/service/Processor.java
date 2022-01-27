package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.Domain.TransactionE;

import java.util.List;

public interface Processor<T> {

    TransactionE addTransaction(TransactionE t);

    void deleteTransaction(Integer id);

    Double total();

    List<T>  listOfIncomeTransaction();

    T findTransaction(Integer id);

    TransactionE updateTransaction(TransactionE t);
}
