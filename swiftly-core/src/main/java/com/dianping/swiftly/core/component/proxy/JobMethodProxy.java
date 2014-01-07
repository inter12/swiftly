package com.dianping.swiftly.core.component.proxy;

import java.lang.reflect.Method;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午3:26
 * 
 * </pre>
 */
public interface JobMethodProxy {

    void doAfter(Object proxy, Method method, Object[] args, Object invoke);

    void doBefore(Object proxy, Method method, Object[] args);

    boolean proxyCheck(Method method, Object[] args);
}
