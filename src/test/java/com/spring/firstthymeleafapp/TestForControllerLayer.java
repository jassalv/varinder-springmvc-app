package com.spring.firstthymeleafapp;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import com.spring.firstthymeleafapp.dto.TransactionMapper;
import com.spring.firstthymeleafapp.model.TransactionResource;
import com.spring.firstthymeleafapp.model.TransactionType;
import com.spring.firstthymeleafapp.service.Calculator;
import com.spring.firstthymeleafapp.service.TransactionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class TestForControllerLayer {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    Calculator calculator;

    @SneakyThrows
    @Test
    void testViewTransaction() {
        TransactionResource transaction = TransactionResource.builder().transactionType(TransactionType.INCOME)
                .id(1).amount(200.0).name("gas").build();
        TransactionEntity transactionEntity = TransactionMapper.INSTANCE.toEntity(transaction);
        BDDMockito.given(transactionService.findTransactionById(1)).willReturn(transactionEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/home/view/1"))
                .andExpect(status().isOk()).andExpect(model().attribute("transaction",
                        TransactionMapper.INSTANCE.toDto(transactionEntity)))
                .andExpect(model().attribute("type", "Income"))
                .andExpect(view().name("view"));
    }


    @SneakyThrows
    @Test
    void testEditTransactionByID() {


        TransactionEntity tracker = TransactionEntity.builder().id(1).name("name").amount(200.00).transactionType(TransactionType.INCOME).build();
        List<TransactionEntity> transactionEntityList = new ArrayList<>();
        transactionEntityList.add(tracker);
        BDDMockito.given(transactionService.findTransactionById(ArgumentMatchers.any())).willReturn(tracker);
        BDDMockito.given(calculator.totalBalance()).willReturn(780.0);
        BDDMockito.given(calculator.totalExpense()).willReturn(0.0);
        BDDMockito.given(calculator.totalIncome()).willReturn(200.0);
        BDDMockito.given(transactionService.listOfIncomeTransaction()).willReturn(transactionEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/home/edit/1"))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("totalbalance", calculator.totalBalance()))
                .andExpect(model().attribute("totalincome", calculator.totalIncome()))
                .andExpect(model().attribute("totalexpenses", calculator.totalExpense()))
                .andExpect(model().attribute("incometrackerlist", transactionService.listOfIncomeTransaction().stream()
                        .map(TransactionMapper.INSTANCE::toDto).collect(Collectors.toList())))
                .andExpect(model().attribute("transaction", transactionService.findTransactionById(1)));
    }

//    @SneakyThrows
//    @Test
//    void testHomePage() {
//
//
//        List<TransactionEntity> transactionEntityList = new ArrayList<>();
//        TransactionResource transactionResource = TransactionResource.builder().id(1).name("name").amount(200.00).transactionType(TransactionType.INCOME).build();
//        BDDMockito.given(calculator.totalBalance()).willReturn(780.0);
//        BDDMockito.given(calculator.totalExpense()).willReturn(0.0);
//        BDDMockito.given(calculator.totalIncome()).willReturn(200.0);
//        BDDMockito.given(transactionService.listOfIncomeTransaction()).willReturn(transactionEntityList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
//                .andExpect(view().name("home"))
//                .andExpect(model().attribute("totalbalance", calculator.totalBalance()))
//                .andExpect(model().attribute("totalincome", calculator.totalIncome()))
//                .andExpect(model().attribute("totalexpenses", calculator.totalExpense()))
//                .andExpect(model().attribute("incometrackerlist", transactionService
//                                                             .listOfIncomeTransaction().stream()
//                               .map(TransactionMapper.INSTANCE::toDto).collect(Collectors.toList())))
//                .andExpect(model().attribute("transaction", new TransactionResource()));
//    }
}