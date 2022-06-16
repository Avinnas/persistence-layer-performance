package com.example.persistencelayer.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariDataSourceConfig {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/pool_test" );
        config.setUsername( "root" );
        config.setPassword( "password" );
        config.addDataSourceProperty( "maximumPoolSize" , "100" );
        ds = new HikariDataSource( config );
    }

    private HikariDataSourceConfig() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
