package com.dianping.swiftly.utils.concurrent;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-24
 *  Time: 下午4:28
 * 
 * </pre>
 */
public interface XStep {

    void handle(XDefaultContext context) throws InterruptedException;

    XStep addProcessor(XProcessor processor);

    void setNextStep(XStep second);

    XStep getNextStep();

    String getName();

}
