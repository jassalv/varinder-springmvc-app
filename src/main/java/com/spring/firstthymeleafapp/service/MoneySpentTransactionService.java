package com.spring.firstthymeleafapp.service;
import com.spring.firstthymeleafapp.model.MoneySpendTransaction;
import com.spring.firstthymeleafapp.model.TransactionE;
import com.spring.firstthymeleafapp.repository.MoneySpentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MoneySpentTransactionService implements Processor<MoneySpendTransaction> {


    private List<MoneySpendTransaction> moneySpendTransactions = new ArrayList<>();

    @Autowired
    MoneySpentDao moneySpentDao;

    @Override
    public TransactionE addTransaction(TransactionE moneySpendTransaction) {
        return moneySpentDao.save(moneySpendTransaction);
    }

    @Override
    public void deleteTransaction(Integer id) {
        moneySpentDao.deleteById(id);
    }

    @Override
    public Double total() {
        return listOfIncomeTransaction().stream().mapToDouble(TransactionE::getAmount).sum();
    }

    @Override
    public List<MoneySpendTransaction> listOfIncomeTransaction() {
        moneySpendTransactions=moneySpentDao.fillAll();
        return moneySpendTransactions;
    }

    @Override
    public MoneySpendTransaction findTransaction(TransactionE transactionE) {
        Optional<MoneySpendTransaction> first = listOfIncomeTransaction().stream()
                .filter(p -> p.getId().equals(transactionE.getId())
                        && p.getName().equals(transactionE.getName())).findFirst();
        if(first.isEmpty()){
            return null;
        }
        System.out.println(first.toString());
        return moneySpentDao.findById(transactionE.getId());
    }

    @Override
    public TransactionE updateTransaction(TransactionE moneySpendTransaction) {
        return moneySpentDao.update(moneySpendTransaction);
    }

    @Override
    public MoneySpendTransaction findTransactionById(Integer id) {
        return moneySpentDao.findById(id);
    }
}
