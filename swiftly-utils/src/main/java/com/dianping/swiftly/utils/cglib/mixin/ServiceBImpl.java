package com.dianping.swiftly.utils.cglib.mixin;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午6:16
 * 
 * </pre>
 */
public class ServiceBImpl implements IServiceB {

    @Override
    public void sayHello() {
        System.out.println("say hello!");
    }
}
