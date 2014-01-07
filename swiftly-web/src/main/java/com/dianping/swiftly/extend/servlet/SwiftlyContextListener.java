package com.dianping.swiftly.extend.servlet;

import com.dianping.swiftly.core.SwiftlyScheduling;
import com.dianping.swiftly.core.component.SwiftlyApplicationContext;
import com.dianping.swiftly.extend.SwiftlyInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-16
 *  Time: 下午1:51
 * 
 * </pre>
 */
public class SwiftlyContextListener implements ServletContextListener {

    private static Logger LOGGER = LoggerFactory.getLogger(SwiftlyContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        SwiftlyApplicationContext applicationContext = SwiftlyApplicationContext.getInstance();

        SwiftlyScheduling instance = SwiftlyScheduling.getInstance();
        applicationContext.putSwiftlyScheduling(instance);

        try {
            instance.afterPropertiesSet();
        } catch (Exception e) {
            LOGGER.error("init scheduling error!", e);
            throw new SwiftlyInitException("init scheduling error!", e);
        }

        LOGGER.info("init Swiftly context success!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            // 1. clean all application
            SwiftlyApplicationContext applicationContext = SwiftlyApplicationContext.getInstance();

            // 2. TODO log all running job

            // 3. clean scheduler
            // applicationContext.getSwiftlyScheduling().destroy();

            // last destroy
            // applicationContext.destroy();

            LOGGER.info("Destroy Swiftly context success!");
        } catch (Exception e) {

            LOGGER.error("Destroy listener error!", e);
        }
    }
}
