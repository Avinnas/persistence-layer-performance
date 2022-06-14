package com.example.persistencelayer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

public class DataSourceConfig {

    @Bean(name = "primary_datasource")
    @ConfigurationProperties("spring.datasource")
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "no_pool_datasource")
    @ConfigurationProperties("spring.no-pool-datasource")
    public DataSource dataSource2(){
        return DataSourceBuilder.create().build();
    }
}
