package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.TransactionEResource;

import java.util.List;

public interface Processor<T> {

    TransactionEResource addTransaction(TransactionEResource t);

    void deleteTransaction(Integer id);

    Double total();

    List<T>  listOfIncomeTransaction();

    T findTransaction(TransactionEResource transactionEResource);

    T findTransactionById(Integer id);

    TransactionEResource updateTransaction(TransactionEResource t);

}
