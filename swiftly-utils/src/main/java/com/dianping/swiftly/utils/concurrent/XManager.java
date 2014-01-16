package com.dianping.swiftly.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Iterator;

public class XManager {

    private static Logger LOGGER = LoggerFactory.getLogger(XManager.class);

    // private ThreadPoolTaskExecutor threadPoolTaskExecutor = CommonServiceLocator.getThreadPools();

    public void execute(final XDefaultContext context) throws Exception {

        Assert.notNull(context, "context is null!");

        if (context.getTotalCount() == 0) {
            LOGGER.warn("processor size is empty!");
            return;
        }

        Iterator<XProcessor> iterator = context.processorsIterator();

        XStep step = new DefaultStep();
        while (iterator.hasNext()) {

            final XProcessor processor = iterator.next();
            step.addProcessor(processor);
        }

        execute(context, step);
    }

    public void executeUnlimit(final XDefaultContext context) throws Exception {

        Assert.notNull(context, "context is null!");

        if (context.getTotalCount() == 0) {
            return;
        }
        Iterator<XProcessor> iterator = context.processorsIterator();

        DefaultStep step = new DefaultStep();
        step.setWaitTimeSwitch(true); // 不需要关注超时时间

        while (iterator.hasNext()) {

            final XProcessor processor = iterator.next();
            step.addProcessor(processor);
        }

        execute(context, step);

    }

    public void execute(XDefaultContext context, XStep step) {

        Assert.notNull(context, "context is null!");
        Assert.notNull(step, "step is null!");

        // int totalStep = getStepSize(step);

        try {
            step.handle(context);

            if (step.getNextStep() == null) {
                return;
            }

        } catch (InterruptedException e) {
            LOGGER.error("handle step error. step Name :" + step.getName());
        }

        try {
            step.getNextStep().handle(context);
        } catch (InterruptedException e) {
            LOGGER.error("handle step error. step Name :" + step.getName());
        }
    }

}
