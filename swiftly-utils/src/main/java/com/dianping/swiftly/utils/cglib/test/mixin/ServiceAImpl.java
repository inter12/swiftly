package com.dianping.swiftly.utils.cglib.test.mixin;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午6:16
 * 
 * </pre>
 */
public class ServiceAImpl implements IServiceA {

    @Override
    public void sayHi() {
        System.out.println("say hi!");
    }
}
