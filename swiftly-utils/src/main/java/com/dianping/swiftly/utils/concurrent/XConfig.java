package com.dianping.swiftly.utils.concurrent;

import java.util.concurrent.*;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-9
 *  Time: 下午5:56
 * 
 * </pre>
 */
public class XConfig {

    private int                      corePoolSize             = Runtime.getRuntime().availableProcessors();

    private int                      maxPoolSize              = Runtime.getRuntime().availableProcessors() * 2;

    private int                      keepAliveSeconds         = 60;

    private boolean                  allowCoreThreadTimeOut   = false;

    private int                      queueCapacity            = 2000;

    private final Object             poolSizeMonitor          = new Object();

    private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();

    private ThreadPoolExecutor       threadPoolExecutor;

    public XConfig(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public XConfig(int corePoolSize, int maxPoolSize, int keepAliveSeconds, boolean allowCoreThreadTimeOut,
                   int queueCapacity, RejectedExecutionHandler rejectedExecutionHandler) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveSeconds = keepAliveSeconds;
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        this.queueCapacity = queueCapacity;

        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                                                    createQueue(queueCapacity), rejectedExecutionHandler);
    }

    public XConfig() {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                                                    createQueue(queueCapacity), rejectedExecutionHandler);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {

        return threadPoolExecutor;
    }

    public BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {
            return new LinkedBlockingQueue<Runnable>(queueCapacity);
        } else {
            return new SynchronousQueue<Runnable>();
        }
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public Object getPoolSizeMonitor() {
        return poolSizeMonitor;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }
}
