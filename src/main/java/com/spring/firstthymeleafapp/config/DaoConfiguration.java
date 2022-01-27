package com.spring.firstthymeleafapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("db.properties")
public class DaoConfiguration {

    @Value("${jdbcUrl}")
    String url;
    @Value("${dataSource.user}")
    String username;
    @Value("${dataSource.password}")
    String password;

    @Bean
    public DataSource createDataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);

    }
    @Bean
    public JdbcTemplate createTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(createDataSource());
        return jdbcTemplate;
    }
}
