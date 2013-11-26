package com.dianping.swiftly.utils.component;

import org.springframework.util.Assert;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午2:51
 * 
 * </pre>
 */
public class AssertExtended extends Assert {

    public static void notZero(int object, String message) {

        if (object == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notZero(int n) {
        notZero(n, "[Assertion failed] - this argument is required; it must not be 0");
    }

    public static void isZero(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is required; it must  be 0");
        }
    }

}
