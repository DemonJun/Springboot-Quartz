package com.jundemon.service.quartz;

import com.jundemon.service.quartz.scheduler.job.TestJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzApplicationTests {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Test
    public void startJob() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
                    // 任务执行类
                    .withIdentity("jobTest_1", "jTestGroup")
                    // 任务名，任务组
                    .build();
            // 2、创建Trigger
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("triggerTest_1", "tTestGroup").startNow()
                    .withSchedule(scheduleBuilder).build();
            // 3、创建Scheduler
            scheduler.start();
            // 4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void resumeJob() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // ①获取调度器中所有的触发器组
            List<String> triggerGroups = scheduler.getTriggerGroupNames();
            // ②重新恢复在tgroup1组中，名为trigger1_1触发器的运行
            for (String triggerGroup : triggerGroups) {
                List<String> triggers = scheduler.getTriggerGroupNames();
                for (String trigger : triggers) {
                    Trigger tg = scheduler.getTrigger(new TriggerKey(trigger, triggerGroup));
                    // ②-1:根据名称判断
                    if (tg instanceof SimpleTrigger
                            && tg.getDescription().equals("tgroup1.trigger1_1")) {
                        // ②-1:恢复运行
                        scheduler.resumeJob(new JobKey(trigger,
                                triggerGroup));
                    }
                }

            }
            scheduler.start();
            try {
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
