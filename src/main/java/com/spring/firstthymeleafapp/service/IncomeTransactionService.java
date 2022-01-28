package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.IncomeTransaction;
import com.spring.firstthymeleafapp.model.TransactionE;
import com.spring.firstthymeleafapp.repository.IncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeTransactionService implements Processor<IncomeTransaction> {

    @Autowired
    private IncomeDao incomeDao;

     List<IncomeTransaction> incomeTransactions = new ArrayList<>();

    @Override
    public TransactionE addTransaction(TransactionE incomeTransaction) {
        return incomeDao.save(incomeTransaction);
    }

    public List<IncomeTransaction> listOfIncomeTransaction(){
        incomeTransactions=incomeDao.fillAll();
        return incomeTransactions;
    }
    @Override
    public void deleteTransaction(Integer id) {

        incomeDao.deleteById(id);
    }

    @Override
    public Double total() {
        return listOfIncomeTransaction().stream().mapToDouble(TransactionE::getAmount).sum();
    }

    @Override
    public IncomeTransaction findTransaction(TransactionE transactionE) {
        Optional<IncomeTransaction> first = listOfIncomeTransaction().stream()
                .filter(p -> p.getId().equals(transactionE.getId())
                && p.getName().equals(transactionE.getName())).findFirst();
        if(first.isEmpty()){
            return null;
        }
        return incomeDao.findById(transactionE.getId());
    }

    @Override
    public IncomeTransaction findTransactionById(Integer id) {
        return incomeDao.findById(id);
    }

    public TransactionE updateTransaction(TransactionE incomeTransaction){
        return incomeDao.update(incomeTransaction);


    }
}
