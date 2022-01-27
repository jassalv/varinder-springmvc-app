package com.spring.firstthymeleafapp.abstractclasses;

import com.spring.firstthymeleafapp.Domain.Transaction;

public interface Balance {
    Double calculateTotalBalance(Double amount);

    Transaction findTransaction(Integer id);
}
