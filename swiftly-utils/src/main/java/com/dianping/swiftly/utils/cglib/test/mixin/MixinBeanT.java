package com.dianping.swiftly.utils.cglib.test.mixin;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午6:17
 * 
 * </pre>
 */
public class MixinBeanT {

    public static void main(String[] args) {
        Class[] interfaces = new Class[] { IServiceA.class, IServiceB.class };
        Object[] delegates = new Object[] { new ServiceAImpl(), new ServiceBImpl() };

        Object o = net.sf.cglib.proxy.Mixin.create(interfaces, delegates);

        IServiceA iServiceA = (IServiceA) o;
        iServiceA.sayHi();

        IServiceB iServiceB = (IServiceB) o;
        iServiceB.sayHello();

    }
}
