package com.spring.firstthymeleafapp.dto;

import com.spring.firstthymeleafapp.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    TransactionType transactionType;
    String name = null;
    Double amount = 0.0;
    Integer id;
    Date addedDate;
    Date updatedDate;
}
