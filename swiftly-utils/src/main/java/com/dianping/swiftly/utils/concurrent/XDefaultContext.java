package com.dianping.swiftly.utils.concurrent;

import java.util.*;
import java.util.Map.Entry;

public class XDefaultContext {

    static ThreadLocal<XDefaultContext> contexts   = new ThreadLocal<XDefaultContext>();

    private XConfig                     xConfig;

    private static final Object         configSync = new Object();

    private Map<String, Object>         paramMap   = new HashMap<String, Object>();

    private Map<String, Object>         resultMap  = new HashMap<String, Object>();

    private List<XProcessor>            processors = new ArrayList<XProcessor>();

    public XDefaultContext() {
    }

    public XDefaultContext(XConfig xConfig) {
        this.xConfig = xConfig;
    }

    public static XDefaultContext getContext() {
        return (XDefaultContext) contexts.get();

    }

    public void setContextMap(Map<String, Object> contextMap) {
        getContext().paramMap = contextMap;
    }

    public static void setContext(XDefaultContext context) {
        contexts.set(context);
    }

    public Object getParam(String key) {
        return paramMap.get(key);
    }

    public void setParam(String key, Object value) {
        paramMap.put(key, value);
    }

    public int getTotalCount() {
        return processors.size();
    }

    public void addResult(String key, Object value) {
        resultMap.put(key, value);
    }

    public Iterator<XProcessor> processorsIterator() {

        return processors.iterator();
    }

    public XDefaultContext addLProcessor(XProcessor lProcessor) {
        processors.add(lProcessor);
        return this;
    }

    public Set<Entry<String, Object>> resultEntrySet() {

        return resultMap.entrySet();
    }

    public Map<String, Object> result() {
        return resultMap;
    }

    public Object getReuslt(String key) {
        return resultMap.get(key);
    }

    public XConfig getxConfig() {

        if (xConfig == null) {
            synchronized (configSync) {
                if (xConfig == null) {
                    return new XConfig();
                }
            }
        }
        return xConfig;
    }

    public void setxConfig(XConfig xConfig) {
        this.xConfig = xConfig;
    }
}
