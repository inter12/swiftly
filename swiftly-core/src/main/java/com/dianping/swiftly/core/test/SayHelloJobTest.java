package com.dianping.swiftly.core.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-21
 *  Time: 下午3:57
 * 
 * </pre>
 */
public class SayHelloJobTest implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello!");
    }

    public void sayHello() {

        System.out.println("hello!");

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static void main(String[] args) throws SchedulerException, ParseException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SayHelloJobTest.class).withDescription("haha job").withIdentity("1",
                                                                                                                "group_1").build();

        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName("trigger");
        trigger.setJobGroup("trigger_group");
        trigger.setCronExpression("*/10 * * * * ?");

        sched.scheduleJob(jobDetail, trigger);

        sched.start();

    }

}
