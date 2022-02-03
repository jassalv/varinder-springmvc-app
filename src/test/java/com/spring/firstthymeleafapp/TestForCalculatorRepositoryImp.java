package com.spring.firstthymeleafapp;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.repository.CalculatorRepositoryImp;
import com.spring.firstthymeleafapp.repository.TransactionDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestForCalculatorRepositoryImp {

    @Autowired
    CalculatorRepositoryImp calculatorRepositoryImp;
    @Autowired
    TransactionDao transactionDao;

    @Test
    void testCalculator() {
        TransactionEntity transactionEntity1 = TransactionEntity.builder().
                transactionType(TransactionType.INCOME).amount(34.0).build();
        TransactionEntity transactionEntity = TransactionEntity.builder().
                transactionType(TransactionType.EXPENSE).amount(43.0).build();
        transactionDao.save(transactionEntity1);
        transactionDao.save(transactionEntity);
        Double aDouble = calculatorRepositoryImp.expenseTotal();
        Double aDouble1 = calculatorRepositoryImp.incomeTotal();
        Double aDouble2 = calculatorRepositoryImp.totalBalance();

        Assertions.assertThat(aDouble).isEqualTo(43.0);
        Assertions.assertThat(aDouble1).isEqualTo(34.0);
        Assertions.assertThat(aDouble2).isEqualTo(77.0);


    }
}
