package com.dianping.swiftly.utils.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 责任链模式--默认的模板实现
 * 
 * @author inter12
 * @param <T>
 */
public abstract class RChainTemplateHandle<T> extends RChainHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(RChainTemplateHandle.class);

    @SuppressWarnings("unchecked")
    @Override
    public T handleRequest(RChainContext context) {
        try {

            beforerHook(context);

            T execute = (T) execute(context);

            afterHook(execute, context);

            if (!isContinue) {
                return (T) execute;
            }

        } catch (Exception e) {

            LOGGER.error("handle error !", e);
        }

        if (getNextHandler() == null) {
            return null;
        }

        return (T) getNextHandler().handleRequest(context);
    }

    protected abstract T execute(RChainContext context);

    protected void beforerHook(RChainContext t) {
        // 默认实现
    }

    protected void afterHook(T t, RChainContext context) {
        // 默认实现
    }

}
