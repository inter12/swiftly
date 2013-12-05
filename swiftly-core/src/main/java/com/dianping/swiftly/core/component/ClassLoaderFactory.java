package com.dianping.swiftly.core.component;

import com.dianping.swiftly.utils.component.LoggerHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-3
 *  Time: 下午5:42
 * 
 * </pre>
 */
public final class ClassLoaderFactory {

    private static Logger         LOGGER                = LoggerFactory.getLogger(ClassLoaderFactory.class);

    public static final String    URL_SUFFIX_JAR        = "jar";

    // 默认的仓库路径
    public static String          defaultRepositoryPath = "/data/appdata";

    private static String         rootDir               = defaultRepositoryPath;

    private static URLClassLoader classLoader           = (URLClassLoader) ClassLoader.getSystemClassLoader();

    private static Method         addMethod             = initAddMethod();

    public static ClassLoader createClassLoader(ClassLoader parent) throws FileNotFoundException {

        StandardClassLoader classLoader = null;

        List<URL> urlList = new ArrayList<URL>();
        File baseDir = new File(rootDir);

        if (!baseDir.exists()) {
            LOGGER.error("can not find defaultRepositoryPath");
            throw new FileNotFoundException("base file not find ! file:" + rootDir);
        }

        findURLs(baseDir, urlList);
        URL[] urls = urlList.toArray(new URL[urlList.size()]);

        if (parent == null) {
            classLoader = new StandardClassLoader(urls);
        } else {
            classLoader = new StandardClassLoader(urls, parent);
        }
        return classLoader;

    }

    public static ClassLoader addClassPath() throws FileNotFoundException {

        List<URL> urlList = loadAllURL();

        if (CollectionUtils.isEmpty(urlList)) {
            return ClassLoader.getSystemClassLoader();
        }

        for (URL url : urlList) {
            addURL(url);
        }

        return classLoader;

    }

    private static List<URL> loadAllURL() throws FileNotFoundException {
        List<URL> urlList = new ArrayList<URL>();
        File baseDir = new File(rootDir);

        if (!baseDir.exists()) {
            LOGGER.error("can not find defaultRepositoryPath");
            throw new FileNotFoundException("base file not find ! file:" + rootDir);
        }

        findURLs(baseDir, urlList);

        return urlList;
    }

    private static void addURL(URL file) {
        try {
            addMethod.invoke(classLoader, new Object[] { file });
        } catch (Exception e) {
        }
    }

    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, NoSuchFieldException,
                                          NoSuchMethodException, IllegalAccessException, InstantiationException,
                                          InvocationTargetException {

        LoggerHelper.initLog();

        // String[] path = new String[] { "classpath*:/config/spring/common/appcontext-dao-core.xml",
        // "classpath*:/config/spring/common/appcontext-ibatis-core.xml",
        // "classpath*:/config/spring/appcontext-*", "classpath*:/config/spring/local/appcontext-*" };
        // ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext(path);

        final ClassLoader classLoader = ClassLoaderFactory.createClassLoader(Thread.currentThread().getContextClassLoader());
        ClassLoaderFactory.addClassPath();

        LOGGER.info(" --------------start--------------------- ");
        chonzhu(classLoader);
        // method1(classLoader);
        LOGGER.info(" --------------end--------------------- ");
        // threadADD(classLoader);

        // thread();

        // bootstrapThread.setContextClassLoader(getClassLoader());
        // setSystemProperties();

        // bootstrapThread.start();

        // Class.forName("org.apache.commons.logging.LogFactory");

        //
        // File file = new File("/data/appdata/activity-remindnote-job.jar");
        //
        // Thread.currentThread().setContextClassLoader(classLoader);
        //
        // Class<?> aClass = classLoader.loadClass(clazzName);
        //
        // Method[] declaredMethods = aClass.getDeclaredMethods();
        // for (Method declaredMethod : declaredMethods) {
        // System.out.println("method name:" + declaredMethod.getName());
        // }
        // Method init = aClass.getDeclaredMethod("init");
        // Object o = aClass.newInstance();
        //
        // Object invoke = init.invoke(o, null);
        // System.out.println(invoke.toString());

        // Class<?> aClass1 = Class.forName("com.dianping.swiftly.core.component.ApplicationContext");
    }

    private static void chonzhu(ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException,
                                                        InstantiationException, IllegalAccessException,
                                                        InvocationTargetException {
        String clazzName = "com.dianping.activityjob.thirdparty.xishiqu.XishiquBaseSynHandler";
        Class<?> aClass = Class.forName(clazzName);
        // Class<?> aClass = classLoader.loadClass(clazzName);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
        // Method hehe = aClass.getDeclaredMethod("handler");
        // Object o = aClass.newInstance();
        // Object invoke = hehe.invoke(o);
        // System.out.println(invoke);
    }

    private static void method1(ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException,
                                                        InstantiationException, IllegalAccessException,
                                                        InvocationTargetException {

        String clazzName = "com.dianping.activity.remindnote.root.Root";
        Class<?> aClass = classLoader.loadClass(clazzName);

        Method haha = aClass.getMethod("haha");
        Object o = aClass.newInstance();
        Object invoke = haha.invoke(o);
        System.out.println(invoke);
    }

    private static void threadADD(final ClassLoader classLoader) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    String clazzName = "com.dianping.activity.remindnote.root.Root";

                    Thread.currentThread().setContextClassLoader(classLoader);
                    Class<?> aClass = classLoader.loadClass(clazzName);
                    Object o = aClass.newInstance();

                    Method[] declaredMethods = aClass.getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        System.out.println("method name:" + declaredMethod.getName());
                    }

                    Method haha = aClass.getDeclaredMethod("haha");

                    Object invoke = haha.invoke(o);
                    System.out.println(invoke);

                    // Method init = aClass.getDeclaredMethod("main");
                    // Object o = aClass.newInstance();

                    // Object invoke = init.invoke(o);
                    // System.out.println(invoke.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        t.setContextClassLoader(classLoader);
        t.start();
    }

    private static List<URL> findURLs(File file, List<URL> urls) {

        Assert.notNull(file, "file should not be null!");

        if (file.isDirectory()) {

            File[] files = file.listFiles();
            for (File tempFile : files) {
                findURLs(tempFile, urls);
            }
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

        return urls;

    }

}
