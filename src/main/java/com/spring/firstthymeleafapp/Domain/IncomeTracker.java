package com.spring.firstthymeleafapp.Domain;
import com.spring.firstthymeleafapp.abstractclasses.Tracker;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Getter
@Setter
public class IncomeTracker extends Tracker {
    Double totalIncome=0.0;

    public Double addIncome(Double addedIncome){
        totalIncome += addedIncome;
        super.calculateTotalBalance(addedIncome);
        return totalIncome;
    }
    public Double removeIncome(Double removeIncome){
        totalIncome += removeIncome;
        super.calculateTotalBalance(-removeIncome);
        return totalIncome;
    }

}
