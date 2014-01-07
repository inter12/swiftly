package com.dianping.swiftly.core.component.proxy;

import com.dianping.swiftly.utils.component.LoggerHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-23
 *  Time: 下午3:07
 * 
 * </pre>
 */
public class CglibImplProxy {

    private static Logger         logger   = LoggerFactory.getLogger(CglibImplProxy.class);

    private static CglibImplProxy instance = new CglibImplProxy();

    private CglibImplProxy() {

    }

    public static CglibImplProxy getDefault() {
        return instance;
    }

    public Service createProxy() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AService.class);
        enhancer.setCallback(new CglibMethodInterceptor());
        return (Service) enhancer.create();
    }

    public static void main(String[] args) {

        LoggerHelper.initLog();
        CglibImplProxy aDefault = CglibImplProxy.getDefault();
        aDefault.createProxy().hello();
    }

    public class CglibMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            printInfo(obj, method, args);

            boolean isProxy = proxyCheck(obj, method, args);

            if (isProxy) {
                doBefore(obj, method, args, proxy);
            }

            Object invoke = proxy.invokeSuper(obj, args);

            if (isProxy) {

                doAfter(obj, method, args, proxy);
            }

            return invoke; // To change body of implemented methods use File | Settings | File Templates.
        }

        private void printInfo(Object obj, Method method, Object[] args) {

            // Method[] declaredMethods = obj.getClass().getDeclaredMethods();
            // for (Method declaredMethod : declaredMethods) {
            // logger.info(declaredMethod.getName());
            // }

            // logger.info("obj:" + obj.getClass().toString());
            logger.info("method:" + method.getName());
            if (null != args) {
                for (Object arg : args) {
                    logger.info("args :" + arg);
                }
            }
        }

        public void doAfter(Object obj, Method method, Object[] args, MethodProxy proxy) {
            logger.info("------ method name ------" + method.getName() + " do after ");
        }

        public void doBefore(Object obj, Method method, Object[] args, MethodProxy proxy) {
            logger.info("------ method name ------" + method.getName() + " do before ");
        }

        public boolean proxyCheck(Object obj, Method method, Object[] args) {
            return true; // To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
