package com.spring.firstthymeleafapp;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.repository.TransactionDao;
import com.spring.firstthymeleafapp.service.TransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestForTransactionServiceImp {

    @Autowired
    TransactionService processor;

    @MockBean
    TransactionDao transactionDao;

    TransactionEntity transactionEntity1 = TransactionEntity.builder().id(1).
            transactionType(TransactionType.INCOME).amount(34.0).build();
    TransactionEntity transactionEntity = TransactionEntity.builder().id(2).
            transactionType(TransactionType.EXPENSE).amount(43.0).build();

    @Test
    void testSaveMethod(){
        BDDMockito.given(transactionDao.save(transactionEntity)).willReturn(transactionEntity);
        TransactionEntity transactionEntity3 = processor.addTransaction(this.transactionEntity);
        Assertions.assertThat(transactionEntity3).isEqualTo(transactionEntity);
    }

    @Test
    void testUpdateMethod(){
        BDDMockito.given(transactionDao.update(transactionEntity)).willReturn(transactionEntity);
        TransactionEntity transactionEntity3 = processor.updateTransaction(this.transactionEntity);
        Assertions.assertThat(transactionEntity3).isEqualTo(transactionEntity);
    }

    @Test
    void testFillALlMethod(){
        List<TransactionEntity> transactionEntityList = new ArrayList<>();
        transactionEntityList.add(transactionEntity);
        transactionEntityList.add(transactionEntity1);
        BDDMockito.given(transactionDao.findAll()).willReturn(transactionEntityList);
        List<TransactionEntity> list = processor.listOfIncomeTransaction();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void testFindTransactionByIdMethod(){
        BDDMockito.given(transactionDao.findById(1)).willReturn(transactionEntity);
        TransactionEntity transactionEntity3 = processor.findTransactionById(1);
        Assertions.assertThat(transactionEntity3).isEqualTo(transactionEntity);
    }

}
