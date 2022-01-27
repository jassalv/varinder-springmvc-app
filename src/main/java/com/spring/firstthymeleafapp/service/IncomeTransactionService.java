package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.Domain.IncomeTransaction;
import com.spring.firstthymeleafapp.Domain.TransactionE;
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

    private List<IncomeTransaction> incomeTransactions = new ArrayList<>();

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
        System.out.println(first.toString());
        return incomeDao.findById(transactionE.getId());
    }

    public TransactionE updateTransaction(TransactionE incomeTransaction){
        return incomeDao.update(incomeTransaction);
    }
    @Override
    public IncomeTransaction findTransactionById(Integer id) {
        return incomeDao.findById(id);
    }
}
