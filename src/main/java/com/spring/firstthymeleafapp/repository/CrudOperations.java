package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.dto.TransactionEntity;

import java.util.List;

public interface CrudOperations<T> {

    TransactionEntity save(TransactionEntity t);

    T findById(Integer id);

    List<T> fillAll();

    Integer deleteById(Integer id);

    TransactionEntity update(TransactionEntity t);

}
