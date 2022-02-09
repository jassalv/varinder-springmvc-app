package com.spring.firstthymeleafapp.service;
import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.processor.NoDataFoundException;
import com.spring.firstthymeleafapp.repository.CalculatorRepositoryImp;
import com.spring.firstthymeleafapp.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService implements Processor<TransactionEntity> {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    CalculatorRepositoryImp calculatorRepositoryImp;


    List<TransactionEntity> incomeTransactions = new ArrayList<>();

    @Override
    public TransactionEntity addTransaction(TransactionEntity transactionEntity) {
        return transactionDao.save(transactionEntity);
    }

    @Override
    public List<TransactionEntity> listOfIncomeTransaction() {

        return transactionDao.findAll();
    }

    @Override
    public void deleteTransaction(Integer id) {
        transactionDao.deleteById(id);
    }

    @Override
    public TransactionEntity findTransactionById(Integer id) {
        TransactionEntity byId = transactionDao.findById(id);
        if (byId==null){
            throw new NoDataFoundException(MessageFormat.format("Transaction is not present with given ID : {0}",id));
        }
        return byId;
    }

    public TransactionEntity updateTransaction(TransactionEntity transactionEntity) {
        return transactionDao.update(transactionEntity);
    }

}
