package com.dianping.swiftly.utils.component;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-10-31
 *  Time: 下午7:29
 *  初始化log4j日志的帮助类
 * </pre>
 */
public class LoggerHelper {

    public static final String CLASSPATH_LOG_LOG4J_XML = "classpath:log/log4j.xml";

    private static Logger      LOGGER                  = LoggerFactory.getLogger(LoggerHelper.class);

    public static void initLog() {

        initLog(CLASSPATH_LOG_LOG4J_XML);
    }

    public static void initLog(String path) {

        Assert.notNull(path);

        try {
            DOMConfigurator.configure(ResourceUtils.getURL(path));

            LOGGER.info("init log success!");

        } catch (FileNotFoundException e) {
            LOGGER.error("init log4j error!");
            throw new RuntimeException("init log error, file not found!", e);
        }

    }

    public static void main(String[] args) {

        LoggerHelper.initLog();

    }

}
