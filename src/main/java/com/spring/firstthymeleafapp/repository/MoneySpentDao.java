package com.spring.firstthymeleafapp.repository;

import com.spring.firstthymeleafapp.model.MoneySpendTransaction;
import com.spring.firstthymeleafapp.model.TransactionE;
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
public class MoneySpentDao implements CrudOperations<MoneySpendTransaction> {

    @Autowired
    JdbcTemplate jdbcTemplate;
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

    @Override
    public TransactionE save(TransactionE moneySpendTransaction) {
        String query = "insert into expenses (name, amount) values (?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, moneySpendTransaction.getName());
            ps.setDouble(2, moneySpendTransaction.getAmount());
            return ps;
        }, generatedKeyHolder);
            Optional<Number> id =  Optional.ofNullable(generatedKeyHolder.getKey());
            if(!id.isEmpty()){
                moneySpendTransaction.setId(Integer.parseInt(String.valueOf(id.get())));
            }
        return moneySpendTransaction;
    }

    @Override
    public MoneySpendTransaction findById(Integer id) {
        String query = "select id, name, amount from expenses where id = ?";
        return jdbcTemplate.queryForObject
                (query, new Object[]{id}, new BeanPropertyRowMapper<>(MoneySpendTransaction.class));
    }

    @Override
    public List<MoneySpendTransaction> fillAll() {
        String query = "select id, name, amount from expenses";
        List<MoneySpendTransaction> moneySpendTransactions = new ArrayList<>();
        List<Map<String, Object>> widgetRows = jdbcTemplate.queryForList(query);

        for (Map<String, Object> widgetRow : widgetRows) {
            MoneySpendTransaction moneySpendTransaction = new MoneySpendTransaction();
            moneySpendTransaction.setId((Integer) widgetRow.get("id"));
            moneySpendTransaction.setName((String) widgetRow.get("Name"));
            moneySpendTransaction.setAmount((Double) widgetRow.get("amount"));
            moneySpendTransactions.add(moneySpendTransaction);
        }
        return moneySpendTransactions;
    }

    @Override
    public Integer deleteById(Integer id) {
        String query = "delete from expenses where Id=?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public TransactionE update(TransactionE moneySpendTransaction) {
        String query = "update expenses set name=?, amount=? where Id=?";
        Object[] args = new Object[]{moneySpendTransaction.getName(), moneySpendTransaction.getAmount(), moneySpendTransaction.getId()};
        jdbcTemplate.update(query, args);
        return moneySpendTransaction;
    }

}
