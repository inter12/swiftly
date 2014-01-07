package com.dianping.swiftly.core.component.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午3:43
 * 
 * </pre>
 */
public class AService implements Service {

    private static Logger logger = LoggerFactory.getLogger(CglibImplProxy.class);

    @Override
    public void hello() {
        logger.info("this is hello method!");
    }

    @Override
    public void helloWorld(String name) {
        logger.info("hello world :" + name);
    }
}
