package com.spring.firstthymeleafapp;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.repository.TransactionDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExpenseTrackerRepositoryLayerTest {

    @Autowired
    TransactionDao dao;

    static TransactionEntity transactionEntity;
    static TransactionEntity transactionEntity1;
    static TransactionEntity testDeleteTransactionEntity;
    static List<TransactionEntity> transactionEntityList = new ArrayList<>();
    static List<TransactionEntity> transactionEntityListAfterSavingIntoDb = new ArrayList<>();

    @BeforeAll
    public static void prepareForTest() {
        transactionEntity = TransactionEntity.builder().
                transactionType(TransactionType.INCOME).name("gas").amount(23.0).build();
        transactionEntity1 = TransactionEntity.builder().
                transactionType(TransactionType.INCOME).amount(34.0).build();
        testDeleteTransactionEntity = TransactionEntity.builder().
                transactionType(TransactionType.EXPENSE).amount(43.0).build();
        transactionEntityList.add(transactionEntity);
        transactionEntityList.add(transactionEntity1);

    }

    @Test
    void textForSavingIntoDatabase() {
        TransactionEntity save = dao.save(transactionEntity);
        TransactionEntity save1 = dao.save(transactionEntity1);
        transactionEntityListAfterSavingIntoDb.add(save);
        transactionEntityListAfterSavingIntoDb.add(save1);
        TransactionEntity transactionEntityOptional = dao.findById(save.getId());
        Assertions.assertThat(transactionEntityOptional).isEqualTo(save);
    }

    @Test
    void checkDeleteOperation() {
        TransactionEntity save3 = dao.save(testDeleteTransactionEntity);
        dao.deleteById(save3.getId());
        Assertions.assertThat(testDeleteTransactionEntity).isNotIn(dao.findAll());
    }

    @Test
    void checkForFindAll() {
        List<TransactionEntity> transactionEntities = dao.findAll();
        Assertions.assertThat(transactionEntities).isEqualTo(transactionEntityListAfterSavingIntoDb);
    }

    @Test
    public void testUpdateExpense() {
        TransactionEntity transactionEntity = TransactionEntity.builder().name("grocery")
                .amount(34.0).transactionType(TransactionType.EXPENSE).build();

        TransactionEntity e = dao.save(transactionEntity);
        transactionEntityListAfterSavingIntoDb.add(e);
        TransactionEntity gas = e.toBuilder().name("gas").amount(50.0).build();
        dao.update(gas);
        Assertions.assertThat(dao.findById(e.getId()).getName()).isEqualTo("gas");
    }
}
