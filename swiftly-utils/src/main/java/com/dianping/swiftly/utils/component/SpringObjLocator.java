package com.dianping.swiftly.utils.component;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 接入spring的工厂类
 * 
 * @author inter12
 */
public class SpringObjLocator {

    private static ApplicationContext applicationContext;

    private static void init() {
        initConifg();
    }

    static {
        initConifg();
    }

    private static void initConifg() {
        String[] path = new String[] { "classpath*:/config/spring/common/appcontext-dao-core.xml",
                "classpath*:/config/spring/common/appcontext-ibatis-core.xml",
                "classpath*:/config/spring/appcontext-*", "classpath*:/config/spring/local/appcontext-*" };

        applicationContext = new ClassPathXmlApplicationContext(path);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (null == applicationContext) {
            init();
        }
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {

        if (null == applicationContext) {
            init();
        }

        return (T) BeanFactoryUtils.beanOfType(applicationContext, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringObjLocator.applicationContext = applicationContext;
    }
}
