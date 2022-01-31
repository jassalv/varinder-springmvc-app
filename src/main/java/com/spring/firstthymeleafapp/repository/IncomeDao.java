package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.model.TransactionEResource;
import com.spring.firstthymeleafapp.model.TransactionSummary;
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
import java.util.Optional;

@Repository
public class IncomeDao implements CrudOperations<TransactionEResource>{

    @Autowired
    JdbcTemplate jdbcTemplate;
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    
    @Override
    public TransactionEResource save(TransactionEResource transactionEResource) {
        String query = "insert into transactions (name, amount,type) values (?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transactionEResource.getName());
            ps.setDouble(2, transactionEResource.getAmount());
            ps.setString(3,transactionEResource.getTransactionType().toString());
            return ps;
            }, generatedKeyHolder);
        Optional<Number> id =  Optional.ofNullable(generatedKeyHolder.getKey());
        id.ifPresent(number -> transactionEResource.setId(Integer.parseInt(String.valueOf(number))));
        return transactionEResource;
    }

    @Override
    public TransactionEResource findById(Integer id) {
        String query = "select id, name, amount from transactions where id = ?";
        return jdbcTemplate.queryForObject
                (query, new Object[]{id}, new BeanPropertyRowMapper<>(TransactionEResource.class));
    }

    @Override
    public List<TransactionEResource> fillAll() {
        String query = "select id, name, amount from transactions";
        List<TransactionEResource> incomeTransactions = new ArrayList<>();
        List<Map<String, Object>> widgetRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> widgetRow : widgetRows) {
            TransactionEResource transactionEResource = new TransactionEResource();
            transactionEResource.setId((Integer) widgetRow.get("id"));
            transactionEResource.setName((String) widgetRow.get("name"));
            transactionEResource.setAmount((Double) widgetRow.get("amount"));
            incomeTransactions.add(transactionEResource);
        }
        return incomeTransactions;
    }

    @Override
    public Integer deleteById(Integer id) {
        String query = "delete from transactions where Id=?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public TransactionEResource update(TransactionEResource transactionEResource) {
        String query = "update transactions set name=?, amount=? where Id=?";
        Object[] args = new Object[]{transactionEResource.getName(), transactionEResource.getAmount(), transactionEResource.getId()};
        jdbcTemplate.update(query, args);
        return transactionEResource;
    }

    @Override
    public TransactionSummary findSum() {
String query = "select sum(if(type=\"INCOME\",amount,0)) as income,sum(if(type=\"EXPENSE\",amount,0)) as expense, sum(amount) as balance from transactions;";
        return jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(TransactionSummary.class));
    }
}

