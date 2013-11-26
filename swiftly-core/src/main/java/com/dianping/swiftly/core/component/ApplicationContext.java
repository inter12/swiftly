package com.dianping.swiftly.core.component;

import com.dianping.swiftly.core.LifeCycle;
import com.dianping.swiftly.core.SwiftlyScheduling;
import com.dianping.swiftly.core.TaskMonitor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-16
 *  Time: 下午3:06
 *  单例
 * </pre>
 */
public class ApplicationContext implements LifeCycle {

    private static ApplicationContext instance  = new ApplicationContext();

    public static final String        SCHEDULER = "scheduler";

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        return instance;
    }

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

    public Object getObjectByClazz(Class clazz) {

        Assert.notNull(clazz, "clazz should not be null!");
        Object object = map.get(clazz.toString());
        if (object == null) {

            object = BeanUtils.instantiateClass(clazz);
            Object cacheObject = map.putIfAbsent(clazz.toString(), object);
            if (cacheObject != null) {
                object = cacheObject;
            }
            return object;
        }

        throw new IllegalStateException(
                                        "clazz is not in anything classLoader or has not default construction ,please check! clazz name :"
                                                + clazz.getName());

    }

    public Object getObject(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public SwiftlyScheduling getSwiftlyScheduling() {
        return (SwiftlyScheduling) getObjectByClazz(SwiftlyScheduling.class);
    }

    public void putSwiftlyScheduling(SwiftlyScheduling scheduling) {
        map.put(SwiftlyScheduling.class.toString(), scheduling);
    }

    public TaskMonitor getTaskMonitor() {
        return (TaskMonitor) getObjectByClazz(TaskMonitor.class);
    }

    public void putTaskMonitor(TaskMonitor taskMonitor) {
        map.put(TaskMonitor.class.toString(), taskMonitor);
    }

    public ConfigurationManager getConfigurationManger() {
        return (ConfigurationManager) getObjectByClazz(ConfigurationManager.class);
    }

    public void putConfigurationManager(ConfigurationManager cm) {
        map.put(ConfigurationManager.class.toString(), cm);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {
        if (map.size() == 0) {
            return;
        }

        map.clear();
    }
}
