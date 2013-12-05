package com.dianping.swiftly.webapp;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-4
 *  Time: 下午10:59
 * 
 * </pre>
 */
public class SwiftlyInitException extends RuntimeException {

    public SwiftlyInitException(Exception e) {
        super(e);
    }

    public SwiftlyInitException(String message, Exception e) {
        super(message, e);
    }

    public SwiftlyInitException(String message) {
        super(message);
    }

}
