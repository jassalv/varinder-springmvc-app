package com.spring.firstthymeleafapp.validator;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidator implements Validator<TransactionEResource> {

    @Autowired
    TransactionService transactionService;



    @Override
    public Boolean isIncomeTransaction(TransactionEResource transactionEResource) {

        return transactionEResource.getAmount() > 0;

    }


}
