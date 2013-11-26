package com.dianping.swiftly.core.component;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-17
 *  Time: 下午4:46
 * 
 * </pre>
 */
public interface SwiftlyClassLoader {

    public static final String URL_SUFFIX_JAR = "jar";

    public Class loadClass(String clazzName) throws Exception;

    public void setRootDir(String rootDir);
}
