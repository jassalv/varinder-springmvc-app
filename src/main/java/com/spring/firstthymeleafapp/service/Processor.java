package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.dto.TransactionEntity;

import java.util.List;

public interface Processor<T> {

    TransactionEntity addTransaction(TransactionEntity t);

    void deleteTransaction(Integer id);

    List<T>  listOfIncomeTransaction();

    T findTransactionById(Integer id);

    TransactionEntity updateTransaction(TransactionEntity t);

}
