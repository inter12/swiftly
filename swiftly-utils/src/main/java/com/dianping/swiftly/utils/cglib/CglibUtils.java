package com.dianping.swiftly.utils.cglib;

import net.sf.cglib.core.KeyFactory;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午5:32
 * 
 * </pre>
 */
public class CglibUtils {

    // BulkBean bulkBean = BulkBean.create()

    private interface MyFactory {

        public Object newInstance(int a, char[] b, String d);
    }

    public static void main(String[] args) {
        MyFactory f = (MyFactory) KeyFactory.create(MyFactory.class);
        Object key1 = f.newInstance(20, new char[] { 'a', 'b' }, "hello");
        Object key2 = f.newInstance(20, new char[] { 'a', 'b' }, "hello");
        Object key3 = f.newInstance(20, new char[] { 'a', '_' }, "hello");
        System.out.println(key1.equals(key2));
        System.out.println(key2.equals(key3));
    }
}
