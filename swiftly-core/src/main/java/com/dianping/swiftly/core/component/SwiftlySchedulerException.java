package com.dianping.swiftly.core.component;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-20
 *  Time: 下午7:25
 * 
 * </pre>
 */
public class SwiftlySchedulerException extends Exception {

    public SwiftlySchedulerException(Exception e) {
        super(e);
    }

    public SwiftlySchedulerException(String message, Exception e) {
        super(message, e);
    }

    public SwiftlySchedulerException(String message) {
        super(message);
    }

}
