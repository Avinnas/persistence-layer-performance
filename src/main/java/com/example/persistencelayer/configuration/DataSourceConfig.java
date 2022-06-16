package com.example.persistencelayer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {

    @Bean(name = "primary_datasource")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Profile("single_table_inheritance")
    public DataSource singleTableInheritanceDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/single_table?rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    };
}
