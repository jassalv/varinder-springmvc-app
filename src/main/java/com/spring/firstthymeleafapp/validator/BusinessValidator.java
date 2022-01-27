package com.spring.firstthymeleafapp.validator;
import com.spring.firstthymeleafapp.Domain.TransactionE;
import com.spring.firstthymeleafapp.service.IncomeTransactionService;
import com.spring.firstthymeleafapp.service.MoneySpentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidator implements Validator<TransactionE>{

    @Autowired
    IncomeTransactionService incomeTransactionService;
    @Autowired
    MoneySpentTransactionService moneySpentTransactionService;
    @Override

    public Boolean isIncomeTransaction(TransactionE transactionE){
        if(transactionE.getAmount()>0){
            incomeTransactionService.addTransaction(transactionE);
            return true;
        }else {
            moneySpentTransactionService.addTransaction(transactionE);
            return false;
        }
    }
}
