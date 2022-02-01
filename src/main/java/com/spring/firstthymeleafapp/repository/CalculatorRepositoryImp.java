package com.spring.firstthymeleafapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CalculatorRepositoryImp implements CalculatorRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Double totalBalance() {
        String query = "select sum(amount) from transactions";
        return jdbcTemplate.queryForObject(query, Double.class);
    }

    @Override
    public Double incomeTotal() {
        String query = "select sum(amount) from transactions where type=?";
        return jdbcTemplate.queryForObject(query, new Object[]{"INCOME"}, Double.class);
    }

    @Override
    public Double expenseTotal() {
        String query = "select sum(amount) from transactions where type=?";
        return jdbcTemplate.queryForObject(query, new Object[]{"EXPENSE"}, Double.class);
    }
}