package com.dianping.swiftly.core;

import java.util.Map;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-17
 *  Time: 下午3:05
 * 
 *  上下文域
 * </pre>
 */
public class SwiftlyContext {

    static ThreadLocal<SwiftlyContext> swiftlyContext = new ThreadLocal<SwiftlyContext>();

    private Map<String, Object>        contextMap;

    public SwiftlyContext(Map<String, Object> context) {
        this.contextMap = context;
    }

    public void setContextMap(Map<String, Object> context) {
        this.contextMap = context;
    }

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public static SwiftlyContext getContext() {
        return swiftlyContext.get();
    }

    public static void setContext(SwiftlyContext context) {
        swiftlyContext.set(context);
    }

}
