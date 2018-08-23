package com.jundemon.service.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.quartz.utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: jundemon
 * @date: 2018年08月23日
 **/
@Data
public class QuartzDruidConnectionProvider implements ConnectionProvider {
    private String driver;
    private String URL;
    private String user;
    private String password;
    private int maxConnections;
    private String validationQuery;

    private static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;

    private DruidDataSource druidDataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    @Override
    public void shutdown() {
        druidDataSource.close();

    }

    @Override
    public void initialize() throws SQLException {
        if (this.URL == null) {
            throw new SQLException("DBPool could not be created: DB URL cannot be null");
        }
        System.out.println("URL:" + URL);
        if (this.driver == null) {
            throw new SQLException("DBPool driver could not be created: DB driver class name cannot be null!");
        }

        if (this.maxConnections < 0) {
            throw new SQLException("DBPool maxConnectins could not be created: Max connections must be greater than zero!");
        }

        druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setDriverClassName(this.driver);
        } catch (Exception ignored) {
        }

        druidDataSource.setUrl(this.URL);
        druidDataSource.setUsername(this.user);
        druidDataSource.setPassword(this.password);
        druidDataSource.setMaxActive(this.maxConnections);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxWait(0);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION);

        if (this.validationQuery != null) {
            druidDataSource.setValidationQuery(this.validationQuery);
            druidDataSource.setTestOnReturn(false);
        }
    }
}
