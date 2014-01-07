package com.dianping.swiftly.utils.cglib.lazyload;

import net.sf.cglib.proxy.Dispatcher;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午5:18
 * 
 * </pre>
 */
public class DispatcherLazy implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("---- start lazy load ----");
        return new TestBean("haha", "dispatcher");
    }
}
