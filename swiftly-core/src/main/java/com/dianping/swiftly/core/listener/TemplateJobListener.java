package com.dianping.swiftly.core.listener;

import org.quartz.JobListener;

import com.dianping.swiftly.core.SwiftlyScheduling;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-16
 *  Time: 下午3:51
 * 
 * </pre>
 */
public abstract class TemplateJobListener implements JobListener {

    protected SwiftlyScheduling swiftlyScheduling;

    protected TemplateJobListener() {
    }

    public TemplateJobListener(SwiftlyScheduling swiftlyScheduling) {
        this.swiftlyScheduling = swiftlyScheduling;
    }

    public void setSwiftlyScheduling(SwiftlyScheduling swiftlyScheduling) {
        this.swiftlyScheduling = swiftlyScheduling;
    }
}
