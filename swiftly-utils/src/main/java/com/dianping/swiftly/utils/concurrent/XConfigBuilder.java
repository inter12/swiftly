package com.dianping.swiftly.utils.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-9
 *  Time: 下午8:19
 * 配置的构造器
 * </pre>
 */
public class XConfigBuilder {

    private final XConfig xConfig;

    public static XConfigBuilder newXConfig() {
        return new XConfigBuilder();
    }

    public static XConfigBuilder newXConfig(ThreadPoolExecutor threadPoolExecutor) {
        return new XConfigBuilder(threadPoolExecutor);
    }

    public XConfigBuilder() {
        xConfig = new XConfig();
    }

    public XConfigBuilder(ThreadPoolExecutor threadPoolExecutor) {
        xConfig = new XConfig(threadPoolExecutor);
    }

    public XConfig build() {
        return xConfig;
    }

    public XConfigBuilder withCorePoolSize(int corePoolSize) {
        xConfig.setCorePoolSize(corePoolSize);
        return this;
    }

    public XConfigBuilder withmaxPoolSize(int maxPoolSize) {
        xConfig.setMaxPoolSize(maxPoolSize);
        return this;
    }

    public XConfigBuilder withKeepAliveSeconds(int keepAliveSeconds) {
        xConfig.setKeepAliveSeconds(keepAliveSeconds);
        return this;
    }

    public XConfigBuilder withAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        xConfig.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        return this;
    }

    public XConfigBuilder withQueueCapacity(int queueCapacity) {
        xConfig.setQueueCapacity(queueCapacity);
        return this;
    }

    public XConfigBuilder withRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        xConfig.setRejectedExecutionHandler(rejectedExecutionHandler);
        return this;
    }

}
