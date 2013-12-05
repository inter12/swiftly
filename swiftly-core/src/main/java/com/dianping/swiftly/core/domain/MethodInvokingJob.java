package com.dianping.swiftly.core.domain;

import com.dianping.swiftly.core.component.ApplicationContext;
import com.dianping.swiftly.core.component.FileSystemClassLoaderProxy;
import com.dianping.swiftly.core.component.SwiftlyClassLoader;
import javassist.*;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 下午8:39
 * 
 * </pre>
 */
public class MethodInvokingJob implements Job {

    private Logger             LOGGER;

    public static final String RESULT = "result";

    private String             targetClassName;

    private String             targetMethod;

    private String             targetParameter;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        invoke(context);
    }

    public void invoke(JobExecutionContext context) throws JobExecutionException {

        Assert.notNull(targetClassName, "targetClassName should not be null");
        Assert.notNull(targetMethod, "targetMethod should not be null");

        LOGGER = LoggerFactory.getLogger(MethodInvokingJob.class);

        LOGGER.info("targetClassName:" + targetClassName);
        LOGGER.info("targetMethod:" + targetMethod);
        LOGGER.info("targetParameter:" + targetParameter);

        try {

            Class targetClass = null;
            try {
                targetClass = Class.forName(targetClassName);
            } catch (ClassNotFoundException e) {

                LOGGER.warn("load class from local classpath error! , try to load from default path /data/appdata");

                Collection<Object> values = ApplicationContext.getInstance().values();
                LOGGER.info("start init!--------------");
                for (Object value : values) {
                    LOGGER.info(value.toString());
                }
                LOGGER.info("end init!--------------");

                SwiftlyClassLoader classLoader = (SwiftlyClassLoader) ApplicationContext.getInstance().getObjectByClazz(FileSystemClassLoaderProxy.class);
                targetClass = classLoader.loadClass(targetClassName);
            }
            Assert.notNull(targetClass, "can not find clazzName!");

            Class[] objectClazzs = null;
            Object[] objects = null;
            if (StringUtils.isNotEmpty(targetParameter)) {
                String[] parameter = targetParameter.split(" ");
                if (parameter != null && parameter.length != 0) {

                    objects = new Object[parameter.length];
                    objectClazzs = new Class[parameter.length];
                    for (int i = 0; i < parameter.length; i++) {

                        LOGGER.debug("parameter index:" + i + " value:" + parameter[i]);
                        objects[i] = parameter[i];
                        objectClazzs[i] = parameter[i].getClass();
                    }
                }
            }

            Method method;

            Method[] declaredMethods = targetClass.getDeclaredMethods();
            LOGGER.info("----------- start method ------------");
            for (Method declaredMethod : declaredMethods) {
                LOGGER.info(declaredMethod.getName());
            }
            LOGGER.info("----------- end method ------------");

            if (objectClazzs == null) {
                method = targetClass.getDeclaredMethod(targetMethod);
            } else {
                method = targetClass.getDeclaredMethod(targetMethod, objectClazzs);
            }

            Object targetObject = targetClass.newInstance();
            Object invoke = method.invoke(targetObject, objects);

            context.put(RESULT, invoke);

        } catch (Exception e) {

            LOGGER.error("invoke error!", e);
            throw new JobExecutionException("invoke error!", e);
        }
    }

    public static void main(String[] args) throws Exception {

        String newClazzName = "com.dianping.swiftly.core.domain.MethodInvokingJob$impl";
        String oldClazzName = "com.dianping.swiftly.core.domain.MethodInvokingJob";
        String paramClazz = "org.quartz.JobExecutionContext";
        String JobClazz = "org.quartz.Job";
        String executeMethod = "execute";

        ClassPool aDefault = ClassPool.getDefault();
        CtClass newClazz = aDefault.makeClass(newClazzName);
        newClazz.addInterface(aDefault.get(JobClazz));
        //
        CtClass oldClazz = aDefault.get(oldClazzName);
        CtClass[] param = new CtClass[] { aDefault.get(paramClazz) };
        CtMethod oldExecuteMethod = oldClazz.getDeclaredMethod(executeMethod, param);
        //
        CtMethod newMethod = CtNewMethod.copy(oldExecuteMethod, newClazz, null);
        newMethod.insertBefore("System.out.println(\"haha\");");
        newClazz.addMethod(newMethod);

        // CtClass newClazz = aDefault.get(newClazzName);

        Object o = newClazz.toClass().newInstance();
        Method method = o.getClass().getMethod(executeMethod, JobExecutionContext.class);
        method.invoke(o, new TTest());
        // newClazz.writeFile();

        /*
         * CtClass ctClass = aDefault.get(newClazzName); CtField[] declaredFields = ctClass.getDeclaredFields(); for
         * (CtField declaredField : declaredFields) { System.out.println(declaredField.toString()); }
         */
        // addNewMethod1(newClazzName, paramClazz, aDefault);

        // addNewClass();

        // CtClass ctClass = ClassPool.getDefault().get("com.dianping.swiftly.core.domain.MethodInvokingJob");
        // ctClass.makeUniqueName("new");
        // // StringBuffer body = addNewMethod(ctClass);
        //
        // CtMethod m = CtNewMethod.make("public void xmove(int dx) {\n System.out.println(dx); \n}", ctClass);
        // ctClass.addMethod(m);
        //
        // // ctClass.writeFile();
        //
        // MethodInvokingJob a = (MethodInvokingJob) ctClass.toClass().newInstance();

        // System.out.println(body.toString());
        // a.execute(null);

    }

    public static class TTest implements JobExecutionContext {

        @Override
        public Scheduler getScheduler() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Trigger getTrigger() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Calendar getCalendar() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public boolean isRecovering() {
            return false; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public TriggerKey getRecoveringTriggerKey() throws IllegalStateException {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public int getRefireCount() {
            return 0; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public JobDataMap getMergedJobDataMap() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public JobDetail getJobDetail() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Job getJobInstance() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Date getFireTime() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Date getScheduledFireTime() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Date getPreviousFireTime() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Date getNextFireTime() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getFireInstanceId() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getResult() {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setResult(Object result) {
            // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getJobRunTime() {
            return 0; // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void put(Object key, Object value) {
            // To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object get(Object key) {
            return null; // To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private static void addNewMethod1(String newClazzName, String paramClazz, ClassPool aDefault)
                                                                                                 throws CannotCompileException,
                                                                                                 NotFoundException,
                                                                                                 IOException,
                                                                                                 InstantiationException,
                                                                                                 IllegalAccessException,
                                                                                                 NoSuchMethodException,
                                                                                                 InvocationTargetException {
        CtMethod ctMethod = CtMethod.make("execute", aDefault.get(paramClazz));

        aDefault.insertClassPath("/data/appdata");
        CtClass andRename = aDefault.getAndRename(newClazzName, newClazzName + "$impl");
        CtMethod execute = andRename.getDeclaredMethod("execute");

        execute.insertBefore(" System.out.println(\"haha\");");

        andRename.writeFile("/data/appdata");

        Object o = andRename.toClass().newInstance();
        Method execute1 = o.getClass().getMethod("execute", JobExecutionContext.class);

        Object invoke = execute1.invoke(0, null);
    }

    private static void addNewClass() throws CannotCompileException, NotFoundException, IOException,
                                     InstantiationException, IllegalAccessException, NoSuchMethodException,
                                     InvocationTargetException {

        ClassPool aDefault = ClassPool.getDefault();
        CtClass ctClass1 = aDefault.makeClass("com.dianping.swiftly.core.domain.MethodInvokingJob$impl");

        String bodyString = "{System.out.println(\"Call to method \");}";
        // 为新创建的类新加一个方法execute，无任何参数
        CtMethod n1 = CtNewMethod.make(CtClass.voidType, "execute", null, null, bodyString, ctClass1);
        ctClass1.addMethod(n1);

        ctClass1.writeFile();
        Object oo = ctClass1.toClass().newInstance();

        Method mms = oo.getClass().getMethod("execute", null);
        System.out.println("new class name is : " + oo.getClass().getName());
        System.out.println("new class's method is : " + mms.invoke(oo, null));
        System.out.println("---------------------------------------------");
    }

    private static StringBuffer addNewMethod(CtClass ctClass) throws NotFoundException, CannotCompileException {
        String methodName = "hello";
        CtMethod oldMethod = ctClass.getDeclaredMethod(methodName);

        String newName = methodName + "$impl";
        oldMethod.setName(newName);

        CtMethod newMethod = CtNewMethod.copy(oldMethod, newName, ctClass, null);
        String type = oldMethod.getReturnType().getName();

        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        if (!"void".equals(type)) {
            body.append(type + " result = ");
        }
        body.append(newName + "($$);\n");

        // finish body text generation with call to print the timing
        // information, and return saved value (if not void)
        body.append("System.out.println(\"Call to method " + methodName
                    + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
        if (!"void".equals(type)) {
            body.append("return result;\n");
        }
        body.append("}");

        // replace the body of the interceptor method with generated
        // code block and add it to class
        newMethod.setBody(body.toString());
        ctClass.addMethod(newMethod);
        return body;
    }
}
