package com.dianping.swiftly.utils.cglib.lazyload;

import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午3:38
 * 
 * </pre>
 */
public class DispatchTB {

    private int        cid;

    // 该属性只有在被get方法时候才会被初始化
    private TestBean   testBean;

    private Dispatcher dispatch = new DispatcherLazy();

    public DispatchTB() {

        cid = 123;
        testBean = (TestBean) Enhancer.create(TestBean.class, TestBean.class.getInterfaces(), dispatch);
    }

    int getCid() {
        return cid;
    }

    void setCid(int cid) {
        this.cid = cid;
    }

    TestBean getTestBean() {
        return testBean;
    }

    void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }

    public static void main(String[] args) {
        DispatchTB tb = new DispatchTB();
        System.out.println(" ----- " + tb.getCid() + " ----- ");
        System.out.println("-----" + tb.getTestBean().getId());

    }

}
