package com.jundemon.service.quartz.config;

import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @description:
 * @author: jundemon
 * @date: 2018年08月23日
 **/
@QuartzDataSource
public class QuartzDataSourceConfig extends AbstractDataSourceInitializer {
    protected QuartzDataSourceConfig(DataSource dataSource, ResourceLoader resourceLoader) {
        super(dataSource, resourceLoader);
    }

    @Override
    protected DataSourceInitializationMode getMode() {
        return null;
    }

    @Override
    protected String getSchemaLocation() {
        return null;
    }
}
