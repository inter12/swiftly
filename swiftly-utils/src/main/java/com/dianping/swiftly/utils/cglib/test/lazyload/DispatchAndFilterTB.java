package com.dianping.swiftly.utils.cglib.test.lazyload;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午3:38
 * 
 * </pre>
 */
public class DispatchAndFilterTB {

    private int        cid;

    // 该属性只有在被get方法时候才会被初始化
    private TestBean   testBean;

    private Dispatcher dispatch = new DispatcherLazy();

    public DispatchAndFilterTB() {

        cid = 123;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestBean.class);
        enhancer.setCallbacks(new Callback[] { dispatch, NoOp.INSTANCE });
        enhancer.setCallbackFilter(new CallbackFilter() {

            @Override
            public int accept(Method method) {
                // System.out.println("method name:" + method.getName());
                String name = method.getName();

                // 若是方法名称为getName，使用第一个callback，若是不是，使用下标为1的回调 这里就是 NoOp ,也就是说getName时候才初始化这个类
                if (name.equals("getName")) {
                    return 0;
                }
                return 1;
            }
        });

        testBean = (TestBean) enhancer.create();

    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public TestBean getTestBean() {
        return testBean;
    }

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }

    public static void main(String[] args) {
        DispatchAndFilterTB tb = new DispatchAndFilterTB();
        System.out.println(" ----- " + tb.getCid() + " ----- ");
        System.out.println("-----" + tb.getTestBean().getId());
        System.out.println("-----" + tb.getTestBean().getName());

    }

}
