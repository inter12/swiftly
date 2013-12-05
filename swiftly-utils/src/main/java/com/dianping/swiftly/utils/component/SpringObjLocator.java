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

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath*:/config/spring/*.xml");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (null == applicationContext) {
            throw new NullPointerException("load applicationContext error !");
        }
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {

        if (null == applicationContext) {
            throw new IllegalArgumentException("load applicationContext error !");
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
