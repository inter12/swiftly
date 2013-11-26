package com.dianping.swiftly.core.component;

import com.dianping.swiftly.api.vo.TaskVO;
import org.springframework.util.Assert;

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
public class ConfigurationManager {

    // 默认的仓库路径
    public static String                defaultRepositoryPath = "/data/appdata";

    private static ConfigurationManager instance              = new ConfigurationManager();

    private LinkedHashMap               lruCache              = null;

    private static final int            INITIAL_CAPACITY      = 500;

    private SwiftlyClassLoader          swiftlyClassLoader;

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

    public Class loadClass(TaskVO taskVO) throws Exception {

        if (swiftlyClassLoader == null) {
            swiftlyClassLoader = (SwiftlyClassLoader) ApplicationContext.getInstance().getObjectByClazz(FileSystemClassLoader.class);
        }

        Class clazz = null;
        lock.readLock().lock();
        try {

            clazz = (Class) lruCache.get(taskVO.getRunClass());
            if (clazz == null) {
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {

                    if (clazz == null) {
                        clazz = JavassistHelper.getInstance().createClass(taskVO);
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

    public void setSwiftlyClassLoader(SwiftlyClassLoader swiftlyClassLoader) {
        this.swiftlyClassLoader = swiftlyClassLoader;
    }

    public void setConfigLocation(String rootDir) {

        Assert.notNull(swiftlyClassLoader, "swiftlyClassLoader is null!");
        swiftlyClassLoader.setRootDir(rootDir);
    }

    public void reFreshRepositoryPath() {
        // TODO 重新设置地址后，加载
    }
}
