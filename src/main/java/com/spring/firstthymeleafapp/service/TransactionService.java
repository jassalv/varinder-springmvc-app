package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.model.TransactionSummary;
import com.spring.firstthymeleafapp.repository.IncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements Processor<TransactionEResource> {

    @Autowired
    private IncomeDao incomeDao;

    List<TransactionEResource> incomeTransactions = new ArrayList<>();

    @Override
    public TransactionEResource addTransaction(TransactionEResource incomeTransaction) {
        return incomeDao.save(incomeTransaction);
    }

    public List<TransactionEResource> listOfIncomeTransaction() {
        incomeTransactions = incomeDao.fillAll();
        return incomeTransactions;
    }

    @Override
    public void deleteTransaction(Integer id) {
        incomeDao.deleteById(id);
    }

    @Override
    public Double total() {
        return listOfIncomeTransaction().stream().mapToDouble(TransactionEResource::getAmount).sum();
    }

    @Override
    public TransactionEResource findTransaction(TransactionEResource transactionEResource) {
        Optional<TransactionEResource> first = listOfIncomeTransaction().stream()
                .filter(p -> p.getId().equals(transactionEResource.getId())
                        && p.getName().equals(transactionEResource.getName())).findFirst();
        if (first.isEmpty()) {
            return null;}
        return incomeDao.findById(transactionEResource.getId());}

    @Override
    public TransactionEResource findTransactionById(Integer id) {
        return incomeDao.findById(id);
    }

    public TransactionEResource updateTransaction(TransactionEResource incomeTransaction) {
        return incomeDao.update(incomeTransaction);
    }

    public TransactionSummary findSum(){
        return incomeDao.findSum();
    }
}
