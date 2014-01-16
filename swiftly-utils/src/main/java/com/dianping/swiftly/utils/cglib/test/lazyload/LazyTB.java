package com.dianping.swiftly.utils.cglib.test.lazyload;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午3:38
 * 
 * </pre>
 */
public class LazyTB {

    private int      cid;

    // 该属性只有在被get方法时候才会被初始化
    private TestBean testBean;

    LazyLoader       load = new LazyLoaderLazy();

    public LazyTB() {

        cid = 123;
        testBean = (TestBean) Enhancer.create(TestBean.class, load);
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
        LazyTB tb = new LazyTB();
        System.out.println(" ----- " + tb.getCid() + " ----- ");
        System.out.println("-----" + tb.getTestBean().getId());

    }

}
