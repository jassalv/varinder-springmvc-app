package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.model.TransactionE;

import java.util.List;

public interface CrudOperations<T> {

    TransactionE save(TransactionE t);

    T findById(Integer id);

    List<T> fillAll();

    Integer deleteById(Integer id);

    TransactionE update(TransactionE t);

}
