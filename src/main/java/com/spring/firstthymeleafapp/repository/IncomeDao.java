package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.Domain.IncomeTransaction;
import com.spring.firstthymeleafapp.Domain.MoneySpendTransaction;
import com.spring.firstthymeleafapp.Domain.TransactionE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class IncomeDao implements CrudOperations<IncomeTransaction>{

    @Autowired
    JdbcTemplate jdbcTemplate;
    private KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    
    @Override
    public TransactionE save(TransactionE incomeTransaction) {
        String query = "insert into income (name, amount) values (?,?)";
        Object[] args = new Object[]{incomeTransaction.getId(), incomeTransaction.getName(), incomeTransaction.getAmount()};
        int i = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, incomeTransaction.getName());
            ps.setDouble(2, incomeTransaction.getAmount());
            return ps;
        }, generatedKeyHolder);
        System.out.println(i);
        int id = generatedKeyHolder.getKey().intValue();
        incomeTransaction.setId( id);
        return incomeTransaction;
    }

    @Override
    public IncomeTransaction findById(Integer id) {
        String query = "select id, name, amount from income where id = ?";
        return jdbcTemplate.queryForObject
                (query, new Object[]{id}, new BeanPropertyRowMapper<>(IncomeTransaction.class));
    }

    @Override
    public List<IncomeTransaction> fillAll() {
        String query = "select id, name, amount from income";
        List<IncomeTransaction> incomeTransactions = new ArrayList<>();
        List<Map<String, Object>> widgetRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> widgetRow : widgetRows) {
            IncomeTransaction incomeTransaction = new IncomeTransaction();
            incomeTransaction.setId((Integer) widgetRow.get("id"));
            incomeTransaction.setName((String) widgetRow.get("Name"));
            incomeTransaction.setAmount((Double) widgetRow.get("amount"));
            incomeTransactions.add(incomeTransaction);
        }
        return incomeTransactions;
    }

    @Override
    public Integer deleteById(Integer id) {
        String query = "delete from income where Id=?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public TransactionE update(TransactionE incomeTransaction) {
        String query = "update expenses set name=?, amount=? where Id=?";
        Object[] args = new Object[]{incomeTransaction.getName(), incomeTransaction.getAmount(), incomeTransaction.getId()};
        int out = jdbcTemplate.update(query, args);
        return incomeTransaction;
    }
}

