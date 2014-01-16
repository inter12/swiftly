package com.dianping.swiftly.utils.component;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-4
 *  Time: 上午11:18
 *  简化加载spring的配置文件
 * </pre>
 */
public class SpringHelper {

    public static ApplicationContext loadSpringConfig(String[] path) {

        return new ClassPathXmlApplicationContext(path);
    }
}
