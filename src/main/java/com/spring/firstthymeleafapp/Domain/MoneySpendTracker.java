package com.spring.firstthymeleafapp.Domain;

import com.spring.firstthymeleafapp.abstractclasses.Tracker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Getter
@Setter
public class MoneySpendTracker extends Tracker {

    Double totalExpense=0.0;

    public Double addExpense(Double addedExpense){
        totalExpense +=addedExpense;
        super.calculateTotalBalance(addedExpense);
        return totalExpense;
    }

    public Double removeExpense(Double removedExpense){
        totalExpense -= removedExpense;
        super.calculateTotalBalance(removedExpense);
        return totalExpense;
    }


}
