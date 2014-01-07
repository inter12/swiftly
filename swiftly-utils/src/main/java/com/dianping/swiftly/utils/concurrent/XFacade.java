package com.dianping.swiftly.utils.concurrent;

import org.springframework.util.Assert;

/**
 * 外观的门面
 * 
 * @author inter12
 */
public class XFacade {

    private static XFacade instance = new XFacade();

    private XFacade() {

    }

    public static XFacade getInstance() {
        return instance;
    }

    private static XManager manager = new XManager();

    public void invoke(XDefaultContext context, XStep step) throws Exception {

        Assert.notNull(context, "context is null!");
        Assert.notNull(step, "step is null!");

        manager.execute(context, step);
    }

    public void invoke(XDefaultContext context) throws Exception {

        Assert.notNull(context, "context is null!");

        manager.execute(context);
    }

    public void invokeUnlimit(XDefaultContext context) throws Exception {
        manager.executeUnlimit(context);
    }

}
