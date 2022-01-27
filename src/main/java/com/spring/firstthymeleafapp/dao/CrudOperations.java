package com.spring.firstthymeleafapp.dao;

import java.util.List;

public interface CrudOperations<T> {

    T save(T t);
    T findById(T t);
    List<T> fillAll();
    Integer deleteById();
}
