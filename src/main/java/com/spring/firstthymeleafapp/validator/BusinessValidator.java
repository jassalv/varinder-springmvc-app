package com.spring.firstthymeleafapp.validator;

import com.spring.firstthymeleafapp.model.TransactionResource;
import com.spring.firstthymeleafapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidator implements Validator<TransactionResource> {

    @Autowired
    TransactionService transactionService;

    @Override
    public Boolean isIncomeTransaction(TransactionResource transactionResource) {
        return transactionResource.getAmount() > 0;
    }
}
