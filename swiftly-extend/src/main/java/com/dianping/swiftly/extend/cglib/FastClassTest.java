package com.dianping.swiftly.extend.cglib;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午3:10
 * 
 * </pre>
 */
public class FastClassTest {

    public static void main(String[] args) throws InvocationTargetException {
        FastClassTest test = new FastClassTest();
        test.test();
    }

    private void test() throws InvocationTargetException {
        FastClass fastClass = FastClass.create(Source.class);

        Source sourceClass = (Source) fastClass.newInstance();

        fastClass.invoke("setIntValue", new Class[] { int.class }, sourceClass, new Object[] { 200 });
        Object getIntValue = fastClass.invoke("getIntValue", new Class[] {}, sourceClass, new Object[] {});
        System.out.println("intValue : " + getIntValue.toString());

        FastMethod setIntValueMethod = fastClass.getMethod("setIntValue", new Class[] { int.class });
        System.out.println("method name :" + setIntValueMethod.getJavaMethod().getName() + " index:"
                           + setIntValueMethod.getIndex());

        FastMethod getIntValueMethod = fastClass.getMethod("getIntValue", new Class[] {});
        System.out.println("method name :" + getIntValueMethod.getJavaMethod().getName() + " index:"
                           + getIntValueMethod.getIndex());
    }

}
