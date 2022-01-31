package com.spring.firstthymeleafapp.model;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionEResource {

    TransactionType transactionType;
    String name = null;
    Double amount = 0.0;
    Integer id;

}
