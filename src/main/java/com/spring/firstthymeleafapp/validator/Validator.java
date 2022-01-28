package com.spring.firstthymeleafapp.validator;

public interface Validator<T> {

    Boolean isIncomeTransaction(T t);

}
