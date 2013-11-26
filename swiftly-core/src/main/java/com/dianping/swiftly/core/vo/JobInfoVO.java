package com.dianping.swiftly.core.vo;

import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-22
 *  Time: 上午10:05
 * 
 * </pre>
 */
public class JobInfoVO {

    private JobDetail       jobDetail;

    private CronTriggerImpl trigger;

    public JobInfoVO(JobDetail jobDetail, CronTriggerImpl trigger) {
        this.jobDetail = jobDetail;
        this.trigger = trigger;
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public CronTriggerImpl getTrigger() {
        return trigger;
    }

    public void setTrigger(CronTriggerImpl trigger) {
        this.trigger = trigger;
    }
}
