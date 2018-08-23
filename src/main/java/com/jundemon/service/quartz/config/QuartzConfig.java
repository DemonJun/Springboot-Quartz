package com.jundemon.service.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * @description: quartz配置
 * @author: JunDemon
 * @date: 2018年08月23日
 **/
@Data
@Configuration
@EnableAsync
@EnableScheduling
@ConfigurationProperties(prefix = "spring.quartz.datasource")
public class QuartzConfig {

    private String url;
    private String username;
    private String password;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(QuartzProperties quartzProperties) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        factory.setDataSource(druidDataSource);
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        factory.setQuartzProperties(properties);

        return factory;
    }

}
