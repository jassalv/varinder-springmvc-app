package com.spring.firstthymeleafapp.validator;
import com.spring.firstthymeleafapp.model.TransactionE;
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

    public Boolean checkIfAlreadyExist(TransactionE transaction){
        if (transaction.getId() != null && transaction.getAmount() >= 0) {
            if (moneySpentTransactionService.findTransaction(transaction) == null) {
                incomeTransactionService.updateTransaction(transaction);
                return true;
            }
            moneySpentTransactionService.deleteTransaction(transaction.getId());
            incomeTransactionService.addTransaction(transaction);
            return true;
        } else if (transaction.getId() != null && transaction.getAmount() < 0) {
            if (incomeTransactionService.findTransaction(transaction) == null) {
                moneySpentTransactionService.updateTransaction(transaction);
                return true;
            }
            incomeTransactionService.deleteTransaction(transaction.getId());
            moneySpentTransactionService.addTransaction(transaction);
            return true;
        }
        return false;
    }

    @Override
    public Boolean isIncomeTransaction(TransactionE transactionE){
        return transactionE.getAmount() > 0;
    }

}
