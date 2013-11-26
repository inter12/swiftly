package com.dianping.swiftly.core.component;

import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

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

    private ConcurrentHashMap           map                   = new ConcurrentHashMap();

    private ConfigurationManager() {

    }

    private SwiftlyClassLoader swiftlyClassLoader;

    public static ConfigurationManager getInstance() {
        return instance;
    }

    public static void setDefaultRepositoryPath(String defaultRepositoryPath) {
        ConfigurationManager.defaultRepositoryPath = defaultRepositoryPath;
    }

    public Class loadClass(String clazzName) throws Exception {

        if (swiftlyClassLoader == null) {
            swiftlyClassLoader = (SwiftlyClassLoader) ApplicationContext.getInstance().getObjectByClazz(FileSystemClassLoader.class);
        }

        Class clazz = (Class) map.get(clazzName);
        if (clazz == null) {
            clazz = swiftlyClassLoader.loadClass(clazzName);
            Class newClazz = (Class) map.putIfAbsent(clazzName, clazz);
            if (newClazz != null) {
                clazz = newClazz;
            }
        }

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
