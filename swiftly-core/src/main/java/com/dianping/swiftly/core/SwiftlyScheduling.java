package com.dianping.swiftly.core;

import com.dianping.swiftly.core.component.ApplicationContext;
import com.dianping.swiftly.core.component.ConfigurationManager;
import com.dianping.swiftly.core.component.RepositoryLocator;
import com.dianping.swiftly.core.domain.TaskDomain;
import com.dianping.swiftly.utils.component.LoggerHelper;
import com.dianping.swiftly.utils.component.SpringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:18
 *  保持单实例 任务调度的服务域
 *  @see:SchedulerFactoryBean
 * </pre>
 */
public class SwiftlyScheduling {

    private static Logger            LOGGER   = LoggerFactory.getLogger(SwiftlyScheduling.class);

    private static SwiftlyScheduling instance = new SwiftlyScheduling();

    static {
    }

    private SwiftlyScheduling() {
    }

    public static SwiftlyScheduling getInstance() {
        return instance;
    }

    // 是否已经初始化成功
    private volatile boolean                  isInit;

    // 调度器名字
    private String                            schedulerName;

    // 调度器的创建工厂
    private Class<? extends SchedulerFactory> schedulerFactoryClazz       = StdSchedulerFactory.class;

    // 具体的调度器
    private Scheduler                         scheduler;

    // quartz的属性
    private Properties                        quartzProperties;

    // job运行监听器
    private List<JobListener>                 jobListeners                = new CopyOnWriteArrayList<JobListener>();

    // 表达式的监听器
    private List<TriggerListener>             triggerListeners            = new CopyOnWriteArrayList<TriggerListener>();

    // 调度器的监听器
    private List<SchedulerListener>           schedulerListeners          = new CopyOnWriteArrayList<SchedulerListener>();

    private String                            rootDir;

    private ConfigurationManager              configurationManger;

    private boolean                           exposeSchedulerInRepository = true;

    // 全局的上下文环境
    private ApplicationContext                applicationContext;

    private TaskMonitor                       taskMonitor;

    // 调度器的默认名字
    public static final String                DEFAULT_SCHEDULER_NAME      = SwiftlyScheduling.class.getSimpleName();

    private Lock                              initLock                    = new ReentrantLock();

    private static final int                  TWO_SECOND                  = 2;

    private void addTask() {

        TaskDomain taskDomain = new TaskDomain(scheduler);
        TaskDomain.setJobRepository(RepositoryLocator.getJobRepository());
        taskDomain.initScheduler();
    }

    private void initSysVariable() {

        if (applicationContext == null) {
            applicationContext = ApplicationContext.getInstance();
        }

        applicationContext.putSwiftlyScheduling(this);

        // 类名+当前时间戳
        if (StringUtils.isEmpty(schedulerName)) {
            schedulerName = DEFAULT_SCHEDULER_NAME + Calendar.getInstance().getTime().getTime();
        }

        // 初始化任务监听器
        if (taskMonitor == null) {
            taskMonitor = (TaskMonitor) applicationContext.getTaskMonitor();
        } else {
            applicationContext.putTaskMonitor(taskMonitor);
        }

        // 初始化类加载路径
        if (configurationManger == null) {
            configurationManger = applicationContext.getConfigurationManger();
        } else {
            applicationContext.putConfigurationManager(configurationManger);
        }
    }

    private void addListener() {
        try {
            ListenerManager listenerManager = scheduler.getListenerManager();

            if (listenerManager == null) {
                return;
            }

            if (CollectionUtils.isNotEmpty(jobListeners)) {
                for (JobListener jobListener : jobListeners) {
                    listenerManager.addJobListener(jobListener);
                }
            }

            if (CollectionUtils.isNotEmpty(triggerListeners)) {
                for (TriggerListener triggerListener : triggerListeners) {
                    listenerManager.addTriggerListener(triggerListener);
                }
            }

            if (CollectionUtils.isNotEmpty(schedulerListeners)) {
                for (SchedulerListener schedulerListener : schedulerListeners) {
                    listenerManager.addSchedulerListener(schedulerListener);
                }
            }

        } catch (SchedulerException e) {
            LOGGER.error("add scheduler listener error!", e);
        }
    }

    protected void createScheduler() throws SchedulerException {

        SchedulerFactory schedulerFactory = (SchedulerFactory) BeanUtils.instantiateClass(this.schedulerFactoryClazz);

        // TODO 线程加载机制 还需要改下
        Thread currentThread = Thread.currentThread();
        ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
        boolean overrideClassLoader = (configurationManger != null && !configurationManger.getClass().getClassLoader().equals(threadContextClassLoader));
        if (overrideClassLoader) {
            currentThread.setContextClassLoader(configurationManger.getClass().getClassLoader());
        }
        try {
            SchedulerRepository repository = SchedulerRepository.getInstance();

            synchronized (repository) {

                Scheduler existingScheduler = (schedulerName != null ? repository.lookup(schedulerName) : null);
                Scheduler newScheduler = schedulerFactory.getScheduler();
                if (newScheduler == existingScheduler) {
                    throw new IllegalStateException(
                                                    "Active Scheduler of name '"
                                                            + schedulerName
                                                            + "' already registered "
                                                            + "in Quartz SchedulerRepository. Cannot create a new Spring-managed Scheduler of the same name!");
                }
                if (!this.exposeSchedulerInRepository) {
                    // Need to remove it in this case, since Quartz shares the Scheduler instance by default!
                    repository.remove(newScheduler.getSchedulerName());
                }
                scheduler = newScheduler;

                applicationContext.put(ApplicationContext.SCHEDULER, scheduler);
            }
        } finally {
            if (overrideClassLoader) {
                // Reset original thread context ClassLoader.
                currentThread.setContextClassLoader(threadContextClassLoader);
            }
        }
    }

    public SwiftlyScheduling setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
        return this;
    }

    public SwiftlyScheduling setSchedulerFactory(Class schedulerFactoryClass) {

        if (schedulerFactoryClass == null || !SchedulerFactory.class.isAssignableFrom(schedulerFactoryClass)) {
            throw new IllegalArgumentException("schedulerFactoryClass must implement [org.quartz.SchedulerFactory]");
        }

        this.schedulerFactoryClazz = schedulerFactoryClass;
        return this;
    }

    public SwiftlyScheduling setRootDir(String rootDir) {
        this.rootDir = rootDir;
        return this;
    }

    public SwiftlyScheduling setQuartzProperties(Properties quartzProperties) {
        this.quartzProperties = quartzProperties;
        return this;
    }

    public void setTaskMonitor(TaskMonitor taskMonitor) {
        this.taskMonitor = taskMonitor;
    }

    public SwiftlyScheduling addListener(Class listenerClazz) {

        Assert.notNull(scheduler, "scheduler is null!");

        boolean isJobListener = JobListener.class.isAssignableFrom(listenerClazz);
        boolean isTriggerListener = TriggerListener.class.isAssignableFrom(listenerClazz);
        boolean isSchedulerListener = SchedulerListener.class.isAssignableFrom(listenerClazz);

        if (listenerClazz == null || !isJobListener || !isTriggerListener || !isSchedulerListener) {
            throw new IllegalArgumentException(
                                               "listenerClazz must implement [org.quartz.JobListener] or [org.quartz.TriggerListener] or [org.quartz.SchedulerListener]");
        }

        Object listener = BeanUtils.instantiateClass(listenerClazz);

        if (isJobListener) {
            JobListener jobListener = (JobListener) listener;
            jobListeners.add(jobListener);
        }

        if (isTriggerListener) {
            TriggerListener triggerListener = (TriggerListener) listener;
            triggerListeners.add(triggerListener);
        }

        if (isSchedulerListener) {
            SchedulerListener schedulerListener = (SchedulerListener) listener;
            schedulerListeners.add(schedulerListener);
        }

        return this;
    }

    public static void main(String[] args) throws Exception {

        LoggerHelper.initLog();

        String[] path = { "classpath*:/config/spring/spring-*.xml" };
        org.springframework.context.ApplicationContext applicationContext1 = SpringHelper.loadSpringConfig(path);
        RepositoryLocator.setApplicationContext(applicationContext1);

        SwiftlyScheduling scheduling = SwiftlyScheduling.getInstance();
        scheduling.afterPropertiesSet();

    }

    public void destroy() throws Exception {

        Assert.notNull(scheduler);

        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            LOGGER.error("clear scheduler error!", e);
        }
    }

    public void afterPropertiesSet() throws Exception {
        if (!isInit) {

            initLock.lock();

            try {
                if (!isInit) {

                    // 0. 初始化 系统参数
                    initSysVariable();
                    LOGGER.info("---------------- init sys variable success! ----------------");

                    // 1. 初始化 scheduler
                    createScheduler();
                    LOGGER.info("---------------- create schedule success! ----------------");

                    // 2.todo 加载 quartz的初始化参数

                    // 3. 添加监听器
                    addListener();
                    LOGGER.info("---------------- add listener success! ----------------");

                    // 4. 加载所有的task，并加入 Quartz框架等待执行
                    addTask();
                    LOGGER.info("---------------- add task success! ----------------");

                    // 5. 启动调度器 延时2秒
                    scheduler.startDelayed(TWO_SECOND);
                    LOGGER.info("---------------- start scheduler success! ----------------");

                    isInit = true;

                }
            } finally {
                initLock.unlock();
            }

        }

    }
}
