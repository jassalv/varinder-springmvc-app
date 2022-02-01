package com.spring.firstthymeleafapp.service;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.dto.TransactionMapper;
import com.spring.firstthymeleafapp.model.TransactionResource;
import com.spring.firstthymeleafapp.repository.CalculatorRepositoryImp;
import com.spring.firstthymeleafapp.repository.IncomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements Processor<TransactionResource> {

    @Autowired
    private IncomeDao incomeDao;

    @Autowired
    CalculatorRepositoryImp calculatorRepositoryImp;


    List<TransactionResource> incomeTransactions = new ArrayList<>();

    @Override
    public TransactionResource addTransaction(TransactionResource incomeTransaction) {
        TransactionEntity save = incomeDao.save(TransactionMapper.INSTANCE.toDto(incomeTransaction));
        return TransactionMapper.INSTANCE.toResource(save);
    }

    public List<TransactionResource> listOfIncomeTransaction() {
        incomeTransactions = incomeDao.fillAll().stream()
                .map(TransactionMapper.INSTANCE::toResource)
                .collect(Collectors.toList());
        return incomeTransactions;
    }

    @Override
    public void deleteTransaction(Integer id) {
        incomeDao.deleteById(id);
    }

    @Override
    public Double total() {
        return listOfIncomeTransaction().stream().mapToDouble(TransactionResource::getAmount).sum();
    }

    @Override
    public TransactionResource findTransactionById(Integer id) {
        return TransactionMapper.INSTANCE.toResource(incomeDao.findById(id));
    }

    public TransactionResource updateTransaction(TransactionResource incomeTransaction) {
        TransactionEntity update = incomeDao.update(TransactionMapper.INSTANCE.toDto(incomeTransaction));
        return TransactionMapper.INSTANCE.toResource(update);
    }

}
