package com.jundemon.service.quartz.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: jundemon
 * @date: 2018年08月23日
 **/
@Slf4j
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        log.info(LocalDateTime.now().toString());
    }
}
