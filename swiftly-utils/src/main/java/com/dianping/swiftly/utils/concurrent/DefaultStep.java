package com.dianping.swiftly.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-24
 *  Time: 下午4:29
 * 
 * </pre>
 */
public class DefaultStep implements XStep {

    private static Logger    logger      = LoggerFactory.getLogger(DefaultStep.class);

    private int              maxWaitTime = 5;

    private boolean          waitTimeSwitch;

    private String           name        = "defaultStep";

    private XStep            nextStep;

    private List<XProcessor> processList = new ArrayList<XProcessor>();

    public DefaultStep(String name) {
        this.name = name;
    }

    public DefaultStep() {
    }

    @Override
    public void handle(final XDefaultContext context) throws InterruptedException {

        if (processList.size() == 0) {
            return;
        }

        final ThreadPoolExecutor threadPoolExecutor = context.getxConfig().getThreadPoolExecutor();
        Assert.notNull(threadPoolExecutor, "threadPoolExecutor is null!");

        final CountDownLatch countDownLatch = new CountDownLatch(processList.size());

        for (XProcessor lProcessor : processList) {

            final XProcessor temp = lProcessor;

            threadPoolExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        temp.handle(context);
                    } catch (Exception e) {
                        logger.error("processor handle error !", e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });

        }

        if (waitTimeSwitch) {
            countDownLatch.await();
        } else {
            countDownLatch.await(maxWaitTime, TimeUnit.SECONDS);
        }

    }

    @Override
    public XStep addProcessor(XProcessor processor) {

        processList.add(processor);
        return this;
    }

    @Override
    public void setNextStep(XStep nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public XStep getNextStep() {
        return nextStep;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setWaitTimeSwitch(boolean waitTimeSwitch) {
        this.waitTimeSwitch = waitTimeSwitch;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }
}
