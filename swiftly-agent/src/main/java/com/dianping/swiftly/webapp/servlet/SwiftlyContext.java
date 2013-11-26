package com.dianping.swiftly.webapp.servlet;

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
 *  系统启动的代理
 * </pre>
 */
public class SwiftlyContext implements ServletContextListener {

    private static Logger LOGGER = LoggerFactory.getLogger(SwiftlyContext.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {


        LOGGER.info("init Swiftly context success!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        LOGGER.info("Destroy Swiftly context success!");
    }
}
