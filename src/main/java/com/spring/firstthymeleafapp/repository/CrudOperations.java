package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.model.TransactionSummary;

import java.util.List;

public interface CrudOperations<T> {

    TransactionEResource save(TransactionEResource t);

    T findById(Integer id);

    List<T> fillAll();

    Integer deleteById(Integer id);

    TransactionEResource update(TransactionEResource t);

    TransactionSummary findSum();

}
