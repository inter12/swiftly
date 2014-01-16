package com.dianping.swiftly.utils.cglib;

import net.sf.cglib.proxy.Mixin;

import org.springframework.util.Assert;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-7
 *  Time: 上午10:39
 * 
 * </pre>
 */
public class MixinBean {

    // 需要糅合的对象
    private Object[] delegates;

    // 糅合对象的接口
    private Class[]  classes;

    private MixinBean(Class[] classes, Object[] delegates) {
        this.delegates = delegates;
        this.classes = classes;
    }

    public static Object getInstance(Class[] classes, Object[] delegates) {

        Assert.notNull(classes, "interfaces are null !");
        Assert.notNull(delegates, "delegates are null !");

        return createMixinBean(classes, delegates);

    }

    private static Object createMixinBean(Class[] classes, Object[] delegates) {

        MixinBean mixinBean = new MixinBean(classes, delegates);
        return mixinBean.mixin();
    }

    private Object mixin() {
        Object object = Mixin.create(classes, delegates);
        return object;
    }
}
