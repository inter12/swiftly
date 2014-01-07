package com.dianping.swiftly.core.component.proxy;

import com.dianping.swiftly.utils.component.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午3:25
 * 
 * </pre>
 */
public class JDKImplProxy {

    private static Logger       logger   = LoggerFactory.getLogger(CglibImplProxy.class);

    private static JDKImplProxy instance = new JDKImplProxy();

    public static JDKImplProxy getInstance() {
        return instance;
    }

    private JDKImplProxy() {

    }

    public void execute() {
        Service service = new AService();
        ProxyInvocationHandle handle = new ProxyInvocationHandle(service);
        Service proxyListener = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                                                                 service.getClass().getInterfaces(), handle);

        // proxyListener.hello();
        proxyListener.helloWorld("inter12");

    }

    public static void main(String[] args) {

        LoggerHelper.initLog();
        JDKImplProxy.getInstance().execute();
    }

    public class ProxyInvocationHandle implements InvocationHandler, JobMethodProxy {

        private Object targetObject;

        public ProxyInvocationHandle(Object targetObject) {
            super();
            this.targetObject = targetObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            boolean isNeedProxy = proxyCheck(method, args);

            printInfo(proxy, method, args);

            if (isNeedProxy) {
                doBefore(proxy, method, args);
            }

            Object invoke = method.invoke(targetObject, args);

            if (isNeedProxy) {
                doAfter(proxy, method, args, invoke);
            }

            return invoke;

        }

        public boolean proxyCheck(Method method, Object[] args) {
            return true;
        }

        public void doAfter(Object proxy, Method method, Object[] args, Object invoke) {
            logger.info("------ method name ------" + method.getName() + " do after ");
        }

        public void doBefore(Object proxy, Method method, Object[] args) {
            logger.info("------ method name ------" + method.getName() + " do before ");
        }

        private void printInfo(Object proxy, Method method, Object[] args) {
            logger.info("------ method name ------" + method.getName());
            String s = proxy.getClass().toString();
            logger.info("proxy" + s);
            if (null != args) {
                for (Object arg : args) {
                    logger.info("args :" + arg);
                }
            }
        }
    }

}
