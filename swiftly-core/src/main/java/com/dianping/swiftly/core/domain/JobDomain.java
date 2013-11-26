package com.dianping.swiftly.core.domain;

import com.dianping.swiftly.api.vo.TaskVO;
import com.dianping.swiftly.core.BO.ObjectCopier;
import com.dianping.swiftly.core.Entity.JobEntity;
import com.dianping.swiftly.core.Repository.MySqlJobRepository;
import com.dianping.swiftly.core.component.JavassistHelper;
import com.dianping.swiftly.core.component.RepositoryLocator;
import com.dianping.swiftly.core.component.SwiftlySchedulerException;
import com.dianping.swiftly.core.vo.JobInfoVO;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

import java.text.ParseException;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 下午3:13
 *  实现
 *  * @see MethodInvokingJobDetailFactoryBean
 * </pre>
 */
public class JobDomain {

    private static Logger        LOGGER        = LoggerFactory.getLogger(JobDomain.class);

    private static MySqlJobRepository jobRepository = RepositoryLocator.getJobRepository();

    private static Class<?>      jobDetailImplClass;

    private static Class<?>      triggerImplClass;

    private int                  jobId;

    private TaskVO               taskVO;

    private JobDetail            jobDetail;

    private CronTriggerImpl      trigger;

    public JobDomain() {
    }

    public JobDomain(TaskVO taskVO) {
        this.taskVO = taskVO;
    }

    public JobDomain(int jobId) {

        this.jobId = jobId;
        JobEntity entity = jobRepository.loadEntity(jobId);
        taskVO = ObjectCopier.entity2VO(entity);

    }

    static {
        try {
            jobDetailImplClass = Class.forName("org.quartz.impl.JobDetailImpl");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("find class org.quartz.impl.JobDetailImpl error!");
            jobDetailImplClass = null;
        }

        try {
            triggerImplClass = Class.forName("org.quartz.impl.triggers.CronTriggerImpl");
        } catch (ClassNotFoundException ex) {
            LOGGER.error("find class org.quartz.impl.triggers.CronTriggerImpl error!");
            triggerImplClass = null;
        }

    }

    public void initJobDetail() throws Exception {

        Assert.notNull(taskVO);

        Class<Job> jobClazz = (Class<Job>) JavassistHelper.getInstance().createClass(taskVO);

        if (jobDetailImplClass == null) {

            LOGGER.debug("init impl with default impl :JobDetailImpl");

            jobDetail = JobBuilder.newJob(jobClazz).withIdentity(taskVO.getName(), taskVO.getJobGroup()).storeDurably(true).requestRecovery(true).build();

        } else {

            LOGGER.debug("init impl with  defined impl :" + jobDetailImplClass.toString());

            jobDetail = (JobDetail) BeanUtils.instantiate(jobDetailImplClass);

            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(jobDetail);
            bw.setPropertyValue("name", taskVO.getName());
            bw.setPropertyValue("group", taskVO.getJobGroup());
            bw.setPropertyValue("jobClass", jobClazz);
            bw.setPropertyValue("durability", true);
            bw.setPropertyValue("requestsRecovery", true);
        }

    }

    private void intiJobTrigger() throws ParseException {

        Assert.notNull(taskVO);

        if (triggerImplClass == null) {

            LOGGER.debug("init CronTrigger with  default impl : CronTriggerImpl");
            trigger = new CronTriggerImpl();

        } else {

            LOGGER.debug("init CronTrigger with  defined impl :" + triggerImplClass.toString());
            trigger = (CronTriggerImpl) BeanUtils.instantiate(triggerImplClass);
        }

        trigger.setCronExpression(taskVO.getRunTrigger());
        trigger.setName(taskVO.getName() + "_trigger");
        trigger.setGroup(taskVO.getJobGroup() + "_trigger");
        trigger.setJobKey(jobDetail.getKey());

    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public JobInfoVO assemblyVO() throws SwiftlySchedulerException {

        try {

            Assert.notNull(taskVO);

            // 1.组装jobDetail数据
            initJobDetail();

            // 2.组装trigger
            intiJobTrigger();

            LOGGER.info("task info :" + taskVO.toString());

            return new JobInfoVO(jobDetail, trigger);

        } catch (Exception e) {
            LOGGER.error("add schedule error!", e);
            throw new SwiftlySchedulerException(e);
        }

    }

    public void startJob() throws SwiftlySchedulerException {

    }

    public void setTaskVO(TaskVO taskVO) {
        this.taskVO = taskVO;
    }

    public static void setJobDetailImplClass(Class<?> jobDetailImplClass) {
        JobDomain.jobDetailImplClass = jobDetailImplClass;
    }

    public static void setTriggerImplClass(Class<?> triggerImplClass) {
        JobDomain.triggerImplClass = triggerImplClass;
    }

    public JobKey getJobkey() {

        Assert.notNull(jobDetail);

        return jobDetail.getKey();
    }
}
