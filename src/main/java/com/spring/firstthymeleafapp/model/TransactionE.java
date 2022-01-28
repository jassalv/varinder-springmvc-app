package com.spring.firstthymeleafapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionE {

    String name = null;
    Double amount = 0.0;
    Integer id;

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", amount=" + amount + '\'' + " id " + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransactionE other = (TransactionE) obj;
        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
