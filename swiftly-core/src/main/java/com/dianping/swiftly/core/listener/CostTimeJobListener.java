package com.dianping.swiftly.core.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dianping.swiftly.core.SwiftlyScheduling;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-16
 *  Time: 下午3:50
 * 
 * </pre>
 */
public class CostTimeJobListener extends TemplateJobListener {

    public CostTimeJobListener() {
    }

    public CostTimeJobListener(SwiftlyScheduling swiftlyScheduling) {
        super(swiftlyScheduling);
    }

    @Override
    public String getName() {
        return "hello world!"; // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        // To change body of implemented methods use File | Settings | File Templates.
    }
}
