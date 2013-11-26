package com.dianping.swiftly.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:19
 *   监控task的运行状况
 * </pre>
 */
public class TaskMonitor {

    private ConcurrentHashMap<Class, Object> runningTasks = new ConcurrentHashMap<Class, Object>();

    private static TaskMonitor               instance     = new TaskMonitor();

    private TaskMonitor() {

    }

    public static TaskMonitor getInstance() {
        return instance;
    }

}
