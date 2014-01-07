package com.dianping.swiftly.utils.chain;

import java.util.HashMap;
import java.util.Map;

/**
 * 责任链模式--上下文
 * @author inter12
 *
 */
public class RChainContext {

    static ThreadLocal<RChainContext> contexts = new ThreadLocal<RChainContext>();

    private static final String USER_ID  = "userId";

    Map<String, Object>         context;

    public RChainContext(int userId) {

        context = new HashMap<String, Object>();
        serUserId(userId);

        contexts.set(this);
    }

    public static void setContext(RChainContext context) {
        contexts.set(context);
    }

    public static RChainContext getContext() {
        return contexts.get();
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public int getUserId() {
        return (Integer) context.get(USER_ID);
    }

    public void serUserId(int userId) {
        context.put(USER_ID, userId);
    }

}
