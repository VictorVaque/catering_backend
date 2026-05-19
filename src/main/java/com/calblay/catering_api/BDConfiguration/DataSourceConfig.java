package com.calblay.catering_api.BDConfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement(order = 2)
public class DataSourceConfig {
    @Value("${spring.db.write.url}")
    private String urlWrite;
    @Value("${spring.db.read.url}")
    private String urlRead;

    @Value("${spring.db.username}")
    private String username;

    @Value("${spring.db.password}")
    private String password;

    @Bean
    public DataSource writeDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(urlWrite);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public DataSource readDataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(urlRead);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Primary
    @Bean
    public DataSource dataSource(){
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targets = new HashMap<>();
        targets.put(DbContextHolder.DbType.WRITE, writeDataSource());
        targets.put(DbContextHolder.DbType.READ, readDataSource());

        routingDataSource.setTargetDataSources(targets);
        routingDataSource.setDefaultTargetDataSource(writeDataSource());

        return routingDataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
