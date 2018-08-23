package com.jundemon.service.quartz;

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
    public void contextLoads() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    // 任务执行类
                    .withIdentity("job2_1", "jGroup2")
                    // 任务名，任务组
                    .build();
            // 2、创建Trigger
            SimpleScheduleBuilder builder = SimpleScheduleBuilder
                    // 设置执行次数
                    .repeatSecondlyForTotalCount(10);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2_1", "tGroup2").startNow()
                    .withSchedule(builder).build();
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
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    class MyJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            System.out.println("1111111111111111111111111111111");
        }
    }
}
