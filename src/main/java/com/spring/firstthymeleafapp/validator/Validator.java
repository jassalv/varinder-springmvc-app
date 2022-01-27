package com.spring.firstthymeleafapp.validator;

import com.spring.firstthymeleafapp.Domain.TransactionE;

public interface Validator<T> {

    Boolean isIncomeTransaction(T t);

}
