package com.dianping.swiftly.core;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-22
 *  Time: 上午10:58
 * 
 * </pre>
 */
public interface LifeCycle {

    public void init() throws Exception;

    public void destroy() throws Exception;

}
