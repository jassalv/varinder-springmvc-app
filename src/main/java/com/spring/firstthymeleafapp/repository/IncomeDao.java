package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.dto.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class IncomeDao implements CrudOperations<TransactionEntity> {

    @Autowired
    JdbcTemplate jdbcTemplate;
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

    @Override
    public TransactionEntity save( TransactionEntity transactionEntity) {
        String query = "insert into transactions (name, amount,type,added_date,update_date) values (?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transactionEntity.getName());
            ps.setDouble(2, transactionEntity.getAmount());
            ps.setString(3, transactionEntity.getTransactionType().toString());
            ps.setDate(4, (Date) transactionEntity.getCreatedDate());
            ps.setDate(5, (Date) transactionEntity.getUpdatedDate());
            return ps;
        }, generatedKeyHolder);
        Optional<Number> id = Optional.ofNullable(generatedKeyHolder.getKey());
        id.ifPresent(number -> transactionEntity.setId(Integer.parseInt(String.valueOf(number))));
        return transactionEntity;
    }

    @Override
    public TransactionEntity findById(Integer id) {
        String query = "select id , name, amount from transactions where id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(TransactionEntity.class));
    }

    @Override
    public List<TransactionEntity> fillAll() {
        String query = "select id , name, amount from transactions";
        List<TransactionEntity> incomeTransactions = new ArrayList<>();
        List<Map<String, Object>> widgetRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> widgetRow : widgetRows) {
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setId((Integer) widgetRow.get("id"));
            transactionEntity.setName((String) widgetRow.get("name"));
            transactionEntity.setAmount((Double) widgetRow.get("amount"));
            incomeTransactions.add(transactionEntity);
        }
        return incomeTransactions;
    }

    @Override
    public Integer deleteById(Integer id) {
        String query = "delete from transactions where Id=?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public TransactionEntity update(TransactionEntity transactionEntity) {
        String query = "update transactions set name=?, amount=? where Id=?";
        Object[] args = new Object[]{transactionEntity.getName(), transactionEntity.getAmount(), transactionEntity.getId()};
        jdbcTemplate.update(query, args);
        return transactionEntity;
    }

}

