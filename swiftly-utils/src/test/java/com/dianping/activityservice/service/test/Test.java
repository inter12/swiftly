package com.dianping.activityservice.service.test;

import com.dianping.swiftly.utils.component.LoggerHelper;
import com.dianping.swiftly.utils.component.SpringHelper;
import com.dianping.swiftly.utils.concurrent.*;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-24
 *  Time: 下午5:13
 * 
 * </pre>
 */
public class Test {

    public static void main(String[] args) throws Exception {
        LoggerHelper.initLog();

        String[] path = new String[] { "classpath*:config/spring/local/appcontext-*.xml" };

        ApplicationContext applicationContext = SpringHelper.loadSpringConfig(path);
//        CommonServiceLocator locator = new CommonServiceLocator();
//        locator.setApplicationContext(applicationContext);
        // applicationContext.getBean("CommonThreadPool");

        Object commonThreadPool = CommonServiceLocator.getBean("CommonThreadPool");

        XDefaultContext context = new XDefaultContext();

        XStep first = new DefaultStep("first");
        first.addProcessor(new Processor1()).addProcessor(new Processor2());

        XStep second = new DefaultStep("second");
        second.addProcessor(new Processor3());

        first.setNextStep(second);

        //
        XFacade.getInstance().invoke(context, first);

        // context.addLProcessor(new Processor1()).addLProcessor(new Processor2());
        // XFacade.getInstance().invokeUnlimit(context);

        Set<Map.Entry<String, Object>> entries = context.resultEntrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object value = entry.getValue();
            String key = entry.getKey();
            System.out.println("key:" + key + " value:" + value);

        }

    }
}
