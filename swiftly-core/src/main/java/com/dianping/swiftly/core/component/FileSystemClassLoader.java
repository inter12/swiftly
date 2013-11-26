package com.dianping.swiftly.core.component;

import com.dianping.swiftly.core.BO.TaskBO;
import com.dianping.swiftly.core.listener.CostTimeJobListener;
import com.dianping.swiftly.utils.component.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-16
 *  Time: 下午8:49
 * 
 *  额外的资源加载器
 * </pre>
 */
public class FileSystemClassLoader implements SwiftlyClassLoader {

    private static Logger  LOGGER                = LoggerFactory.getLogger(FileSystemClassLoader.class);

    // 默认的仓库路径
    public String          defaultRepositoryPath = "/data/appdata";

    private String         rootDir               = defaultRepositoryPath;

    private URLClassLoader loader;

    private Lock           lock                  = new ReentrantLock();

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    public void setDefaultRepositoryPath(String defaultRepositoryPath) {
        this.defaultRepositoryPath = defaultRepositoryPath;
    }

    public FileSystemClassLoader() {

    }

    public Class loadClass(String clazzName) throws Exception {

        if (loader == null) {
            lock.lock();
            if (loader == null) {
                try {

                    List<URL> urlList = new ArrayList<URL>();
                    File baseDir = new File(rootDir);

                    if (!baseDir.exists()) {
                        LOGGER.error("can not find defaultRepositoryPath");
                        throw new FileNotFoundException("base file not find ! file:" + rootDir);
                    }

                    findURLs(baseDir, urlList);
                    URL[] urlss = urlList.toArray(new URL[urlList.size()]);

                    loader = new URLClassLoader(urlss);

                    LOGGER.debug("init URLClassLoader success!");

                } finally {
                    lock.unlock();
                }
            }
        }

        return loader.loadClass(clazzName);
    }

    private List<URL> findURLs(File file, List<URL> urls) {

        if (!file.isFile()) {
            return urls;
        }

        if (!file.getName().endsWith(URL_SUFFIX_JAR)) {
            return urls;
        }

        if (!file.canRead()) {

            file.setReadable(true);
        }

        try {
            URL url = file.toURI().toURL();
            urls.add(url);
        } catch (MalformedURLException e) {
            LOGGER.error("load jar error! jar name:" + file.getName(), e);
        }

        if (file.isDirectory()) {

            File[] files = file.listFiles();
            for (File tempFile : files) {
                findURLs(tempFile, urls);
            }
        }

        return urls;

    }

    private File[] listFileName() {

        File baseDir = new File(rootDir);
        return baseDir.listFiles();
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public static void main(String[] args) throws Exception {

        LoggerHelper.initLog();

        File file = new File("/tmp/11.txt");
        URL[] urls = new URL[1];
        urls[0] = file.toURI().toURL();
        URLClassLoader loader = new URLClassLoader(urls);

        String java_home = System.getenv("USER");
        System.out.println("javahome:" + java_home);
        Map<String, String> getenv = System.getenv();

        Set<Map.Entry<String, String>> entries = getenv.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + ":" + value);

        }
        // test();

    }

    private static void test() throws Exception {
        FileSystemClassLoader fileSystemClassLoader = new FileSystemClassLoader("/tmp/jar");

        Class<?> aClass = fileSystemClassLoader.loadClass("com.dianping.swiftly.core.BO.TaskBO");
        TaskBO instantiate = (TaskBO) BeanUtils.instantiate(aClass);

        // Method getName = aClass.getMethod("getName");
        // Object invoke = getName.invoke(instantiate);
        // System.out.println("result:" + invoke);

        if (instantiate == null) {
            System.out.println("result is null!");
        } else {
            System.out.println(instantiate.toString());
        }

        Class clazz = CostTimeJobListener.class;
        CostTimeJobListener o = (CostTimeJobListener) clazz.newInstance();

        Method getName = clazz.getMethod("getName");
        Object invoke = getName.invoke(clazz.newInstance());
        System.out.println("result:" + invoke);

        System.out.println("director resulr:" + o.getName());
    }
}
