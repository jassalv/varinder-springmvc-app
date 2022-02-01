package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.TransactionResource;

import java.util.List;

public interface Processor<T> {

    TransactionResource addTransaction(TransactionResource t);

    void deleteTransaction(Integer id);

    Double total();

    List<T>  listOfIncomeTransaction();

    T findTransactionById(Integer id);

    TransactionResource updateTransaction(TransactionResource t);

}
