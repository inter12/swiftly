package com.dianping.swiftly.utils.component;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Collection;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-10-31
 *  Time: 下午7:29
 *  To change this template use File | Settings | File Templates.
 * </pre>
 */
public class LoggerHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerHelper.class);

    public static void initLog() {
        try {
            DOMConfigurator.configure(ResourceUtils.getURL("classpath:log/log4j.xml"));

            LOGGER.info("init log success!");

        } catch (FileNotFoundException e) {
            LOGGER.error("init log4j error!");
            throw new RuntimeException("init log error!");
        }

    }

    public static void logCollection(Logger Logger, Collection collection) {

        Assert.notNull(Logger);
        if (CollectionUtils.isEmpty(collection)) {
            return;
        }

    }

    public static void main(String[] args) {

        LoggerHelper.initLog();

    }

}
