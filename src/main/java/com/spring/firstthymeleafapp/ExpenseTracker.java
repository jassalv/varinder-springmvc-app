package com.spring.firstthymeleafapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTracker {
    String name=null;
    Double amount=0.0;
    Integer id=0;

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", amount=" + amount + '\'' + " id " + id +
                '}';
    }
}
