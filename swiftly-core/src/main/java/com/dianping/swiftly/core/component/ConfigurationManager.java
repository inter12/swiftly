package com.dianping.swiftly.core.component;

import com.dianping.swiftly.api.vo.TaskVO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:20
 * 
 * </pre>
 */
public class ConfigurationManager implements ApplicationContextAware {

    // 默认的仓库路径
    public static String                defaultRepositoryPath = "/data/appdata/";

    private static ConfigurationManager instance              = new ConfigurationManager();

    private LinkedHashMap               lruCache              = null;

    private static final int            INITIAL_CAPACITY      = 500;

    // private SwiftlyClassLoader swiftlyClassLoader;

    private static ApplicationContext   applicationContext;

    private ReadWriteLock               lock                  = new ReentrantReadWriteLock();

    private ConfigurationManager() {

        lruCache = new LinkedHashMap(INITIAL_CAPACITY + 1, 0.75f, true) {

            public boolean removeEldestEntry(Map.Entry eldest) {
                return size() > INITIAL_CAPACITY;
            }
        };
    }

    public static ConfigurationManager getInstance() {
        return instance;
    }

    public static void setDefaultRepositoryPath(String defaultRepositoryPath) {
        ConfigurationManager.defaultRepositoryPath = defaultRepositoryPath;
    }

    public static void main(String[] args) throws Exception {

        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        instance1.test();
    }

    public void test() throws Exception {
        String[] path = new String[] { "classpath*:/config/spring/common/appcontext-dao-core.xml",
                "classpath*:/config/spring/common/appcontext-ibatis-core.xml",
                "classpath*:/config/spring/appcontext-*", "classpath*:/config/spring/local/appcontext-*" };

        org.springframework.context.ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);

        ClassLoaderFactory.addClassPath();
        Class<?> aClass = Class.forName("com.dianping.activityjob.thirdparty.xishiqu.XishiquBaseSynHandler");
        System.out.println(aClass.toString());
    }

    public Class loadClass(TaskVO taskVO) throws Exception {

        // if (swiftlyClassLoader == null) {
        // swiftlyClassLoader = (SwiftlyClassLoader)
        // ApplicationContext.getInstance().getObjectByClazz(FileSystemClassLoaderProxy.class);
        // }

        Class clazz = null;
        lock.readLock().lock();
        try {

            clazz = (Class) lruCache.get(taskVO.getRunClass());
            if (clazz == null) {
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {

                    if (clazz == null) {

                        // ClassLoader classLoader = ClassLoaderFactory.addClassPath();
                        // Class<?> aClass = (taskVO.getRunClass());

                        // ClassLoaderFactory.addClassPath();

                        // ClassLoader classLoader = ClassLoaderFactory.addClassPath();
                        // ClassLoader classLoader =
                        // ClassLoaderFactory.createClassLoader(Thread.currentThread().getContextClassLoader());

                        // DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
                        // applicationContext.getAutowireCapableBeanFactory();
                        // beanFactory.setBeanClassLoader(classLoader);

                        // 添加额外的JAR包到classpath路径下
                        ClassLoader classLoader = ClassLoaderFactory.addClassPath();
//                        ClassLoader classLoader = ClassLoaderFactory.createClassLoader(ClassLoader.getSystemClassLoader());
                        // Thread.currentThread().setContextClassLoader(classLoader);

                        clazz = classLoader.loadClass(taskVO.getRunClass());
                        // clazz = ClassUtils.forName(taskVO.getRunClass(), this.getClass().getClassLoader());
                        // ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                        // clazz = systemClassLoader.loadClass(taskVO.getRunClass());
                        // ClassLoader classLoader =
                        // ClassLoaderFactory.createClassLoader(Thread.currentThread().getContextClassLoader());
                        // clazz = classLoader.loadClass(taskVO.getRunClass());

                        // clazz = Class.forName(taskVO.getRunClass());
                        // clazz = ClassUtils.forName(taskVO.getRunClass(),
                        // Thread.currentThread().getContextClassLoader());
                        // clazz = swiftlyClassLoader.loadClass(taskVO.getRunClass());
                        lruCache.put(taskVO.getRunClass(), clazz);
                    }

                } finally {
                    lock.readLock().lock();
                    lock.writeLock().unlock();
                }
            }

        } finally {
            lock.readLock().unlock();
        }

        // if (clazz == null) {
        // clazz = swiftlyClassLoader.loadClass(clazzName);
        // Class newClazz = (Class) lruCache.putIfAbsent(clazzName, clazz);
        // if (newClazz != null) {
        // clazz = newClazz;
        // }
        // }

        return clazz;
    }

    // public void setSwiftlyClassLoader(SwiftlyClassLoader swiftlyClassLoader) {
    // this.swiftlyClassLoader = swiftlyClassLoader;
    // }
    //
    // public void setConfigLocation(String rootDir) {
    //
    // Assert.notNull(swiftlyClassLoader, "swiftlyClassLoader is null!");
    // swiftlyClassLoader.setRootDir(rootDir);
    // }

    public void reFreshRepositoryPath() {
        // TODO 重新设置地址后，加载
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurationManager.applicationContext = applicationContext;
    }
}
