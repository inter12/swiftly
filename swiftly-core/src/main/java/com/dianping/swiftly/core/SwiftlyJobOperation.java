package com.dianping.swiftly.core;

import com.dianping.swiftly.core.component.SwiftlyApplicationContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.dianping.swiftly.core.component.RepositoryLocator;
import com.dianping.swiftly.core.domain.JobDomain;
import com.dianping.swiftly.utils.component.AssertExtended;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:18
 *   作业操作的服务域
 * </pre>
 */
public class SwiftlyJobOperation implements InitializingBean {

    private static Logger             LOGGER             = LoggerFactory.getLogger(SwiftlyJobOperation.class);

    private static SwiftlyApplicationContext applicationContext = SwiftlyApplicationContext.getInstance();

    private Scheduler                 scheduler;

    public void executeJob(int jobId) throws Exception {

        AssertExtended.notZero(jobId, "jobId should not be zero!");
        Assert.notNull(scheduler, "scheduler not init!");

        JobDomain jobDomain = new JobDomain(jobId);
        JobDomain.setJobRepository(RepositoryLocator.getJobRepository());
        jobDomain.assemblyVO();
        JobKey jobkey = jobDomain.getJobkey();
        boolean isExisted = scheduler.checkExists(jobkey);

        if (isExisted) {
            scheduler.pauseJob(jobkey);
            scheduler.interrupt(jobkey);
        }

        LOGGER.debug("resume job info. name:" + jobkey.getName() + " group:" + jobkey.getGroup());
        scheduler.resumeJob(jobkey);

    }

    public void init() throws Exception {

    }

    public void destroy() throws Exception {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduler = (Scheduler) applicationContext.getObject(SwiftlyApplicationContext.SCHEDULER);
    }
}
