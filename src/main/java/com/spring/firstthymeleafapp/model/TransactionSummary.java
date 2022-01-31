package com.spring.firstthymeleafapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class TransactionSummary {

    Double income=0.0;
    Double expense=0.0;
    Double balance=0.0;
}
