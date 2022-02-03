package com.spring.firstthymeleafapp.dto;

import com.spring.firstthymeleafapp.model.TransactionType;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class TransactionEntity {
    TransactionType transactionType;
    String name = null;
    Double amount = 0.0;
    Integer id;
    String createdDate;
    String updatedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
