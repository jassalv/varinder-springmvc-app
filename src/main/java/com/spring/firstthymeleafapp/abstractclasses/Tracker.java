package com.spring.firstthymeleafapp.abstractclasses;

import com.spring.firstthymeleafapp.Domain.Transaction;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Tracker implements Balance {
    static Double totalBalance=0.0;
    List<Transaction> transaction = new ArrayList<>();

    @Override
    public Double calculateTotalBalance(Double amount) {
        totalBalance +=amount;
        return totalBalance;
    }

    public Double getTotalBalance(){
        return totalBalance;
    }

    @Override
    public Transaction findTransaction(Integer id) {
        return transaction.stream().filter(p->p.getId().equals(id)).findFirst().get();
    }
}
