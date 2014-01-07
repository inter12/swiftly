package com.dianping.swiftly.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TemplateProcessor implements XProcessor {

    private static Logger logger = LoggerFactory.getLogger(TemplateProcessor.class);

    protected String      name;

    public void handle(XDefaultContext context) {
        Object doWork = null;
        try {
            nameConfig();
            doWork = doWork(context);
        } catch (Exception e) {

            // default handle is ignore error !
            doExeption(e);
            logger.error("processor handle error . name:" + name, e);
        }

        context.addResult(name, doWork);
    }

    /**
     * 异常处理的默认实现，子类可覆盖
     * 
     * @param e
     */
    public void doExeption(Exception e) {

    }

    /**
     * 配置各个子模块的结果集
     */
    protected abstract void nameConfig();

    protected abstract Object doWork(XDefaultContext context);

    public void setName(String name) {
        this.name = name;
    }

}
