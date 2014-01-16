package com.dianping.swiftly.utils.cglib;

import net.sf.cglib.core.KeyFactory;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午5:32
 *   生成唯一key工厂
 * </pre>
 */
public class UniqueFactory {

    private interface Factory {

        public Object newInstance(char[] b, String... d);
    }

    public static void main(String[] args) {
        Factory f = (Factory) KeyFactory.create(Factory.class);
        Object key1 = f.newInstance(new char[] { 'a', 'b' }, "hello");
        Object key2 = f.newInstance(new char[] { 'a', 'b' }, "hello");
        Object key3 = f.newInstance(new char[] { 'a', '_' }, "hello");
        System.out.println(key1.equals(key2));
        System.out.println(key2.equals(key3));
    }
}
