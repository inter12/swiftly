package com.dianping.swiftly.utils.cglib.test.lazyload;

import net.sf.cglib.proxy.LazyLoader;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午3:53
 * 
 * </pre>
 */
public class LazyLoaderLazy implements LazyLoader {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("---- start lazy load ------ ");
        return new TestBean("hehe", "lazy!");
    }
}
