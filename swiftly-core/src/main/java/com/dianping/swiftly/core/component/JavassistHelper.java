package com.dianping.swiftly.core.component;

import com.dianping.swiftly.api.vo.TaskVO;
import com.dianping.swiftly.core.domain.MethodInvokingJob;
import com.dianping.swiftly.utils.component.LoggerHelper;
import javassist.*;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-20
 *  Time: 下午7:36
 * 
 * </pre>
 */
public class JavassistHelper {

    private static Logger          logger             = LoggerFactory.getLogger(JavassistHelper.class);

    private static String          NEW_CLAZZ_NAME     = "com.dianping.swiftly.core.domain.MethodInvokingJob$";
    private static final String    OLD_CLAZZ_NAME     = "com.dianping.swiftly.core.domain.MethodInvokingJob";
    private static final String    PARAM_CLAZZ        = "org.quartz.JobExecutionContext";
    private static final String    JOB_CLAZZ          = "org.quartz.Job";
    private static final String    EXECUTE_METHOD     = "execute";
    private static final String    INVOKE_METHOD      = "invoke";

    private static final String    TARGET_CLASS_NAME  = "targetClassName";
    private static final String    TARGET_METHOD_NAME = "targetMethod";
    private static final String    TARGET_PARAMETER   = "targetParameter";

    private static final String    LOGGER_FIELD       = "LOGGER";
    private static final String    LOGGER_CLAZZ       = "org.slf4j.Logger";
    private static final String    LOGGER_METHOD      = "getLogger";

    private static final String    STRING             = "java.lang.String";

    public static final String     NEW_CLAZZ_DIR      = "/data/appdata/new_clazz";

    private static JavassistHelper instance           = new JavassistHelper();

    private JavassistHelper() {

        File file = new File(NEW_CLAZZ_DIR);
        file.mkdir();
    }

    public static JavassistHelper getInstance() {
        return instance;
    }

    public Class<?> createClass(TaskVO taskVO) throws Exception {

        Assert.notNull(taskVO, "target Clazz should not be null or empty!");
        Assert.notNull(taskVO.getRunClass(), "runClazz should not be null or empty!");

        Class runClass = ConfigurationManager.getInstance().loadClass(taskVO);
        String newClazzName = NEW_CLAZZ_NAME + runClass.getSimpleName();
        Class resultClazz = loadClazz(taskVO, newClazzName);
        return resultClazz;
    }

    public Class loadClazz(TaskVO taskVO, String newClazzName) throws NotFoundException, CannotCompileException,
                                                              IOException {
        ClassPool classPool = ClassPool.getDefault();

        CtClass newClazz = classPool.makeClass(newClazzName);
        newClazz.addInterface(classPool.get(JOB_CLAZZ));

        // 复制需要覆盖的方法(execute)
        CtClass oldClazz = classPool.get(OLD_CLAZZ_NAME);
        CtClass[] param = new CtClass[] { classPool.get(PARAM_CLAZZ) };
        CtMethod oldExecuteMethod = oldClazz.getDeclaredMethod(EXECUTE_METHOD, param);

        CtMethod newExecuteMethod = CtNewMethod.copy(oldExecuteMethod, newClazz, null);
        newClazz.addMethod(newExecuteMethod);

        // 复制需要invoke方法
        CtMethod oldInvokeMethod = oldClazz.getDeclaredMethod(INVOKE_METHOD, param);
        CtMethod newInvokeMethod = CtNewMethod.copy(oldInvokeMethod, newClazz, null);
        newClazz.addMethod(newInvokeMethod);

        // 初始化一些参数
        CtField targetClassNameField = new CtField(classPool.get(STRING), TARGET_CLASS_NAME, newClazz);
        targetClassNameField.setModifiers(Modifier.PRIVATE);
        newClazz.addField(targetClassNameField, CtField.Initializer.constant(taskVO.getRunClass()));

        CtField targetMethodField = new CtField(classPool.get(STRING), TARGET_METHOD_NAME, newClazz);
        targetMethodField.setModifiers(Modifier.PRIVATE);
        newClazz.addField(targetMethodField, CtField.Initializer.constant(taskVO.getRunMethod()));

        CtField targetParameterField = new CtField(classPool.get(STRING), TARGET_PARAMETER, newClazz);
        targetParameterField.setModifiers(Modifier.PRIVATE);
        newClazz.addField(targetParameterField, CtField.Initializer.constant(taskVO.getRunParameter()));

        // 初始化LOGGER
        CtField loggerField = new CtField(classPool.get(LOGGER_CLAZZ), LOGGER_FIELD, newClazz);
        loggerField.setModifiers(Modifier.PRIVATE);

        newClazz.addField(loggerField);
        newClazz.writeFile(NEW_CLAZZ_DIR);

        return newClazz.toClass();
    }

    public void synTest(String name) throws InterruptedException {
        Random ran = new Random(10);

        String newName = name + ran.nextInt();
        synchronized (newName) {
            System.out.println("sleep name:" + name);
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public static void testField() throws Exception {

        ClassPool classPool = ClassPool.getDefault();

        CtClass newClazz = classPool.getAndRename("com.dianping.swiftly.core.BO.TaskBO",
                                                  "com.dianping.swiftly.core.BO.TaskBO$new");
        CtField loggerField = new CtField(classPool.get(LOGGER_CLAZZ), LOGGER_FIELD, newClazz);
        CtField.Initializer getLogger = CtField.Initializer.byCall(classPool.get(LOGGER_CLAZZ), LOGGER_METHOD,
                                                                   new String[] { newClazz.toString() });

        newClazz.addField(loggerField, getLogger);

        Class aClass = newClazz.toClass();
        Field declaredField = aClass.getDeclaredField(LOGGER_FIELD);
        String name = declaredField.getName();
        System.out.println(name);

    }

    public static void main(String[] args) throws Exception {

        // testField();

        LoggerHelper.initLog();

        System.out.println(System.getProperty("sun.boot.class.path"));
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));


        JavassistHelper helper = JavassistHelper.getInstance();
        // helper.synTest("haha");
        // helper.synTest("hehe");

        TaskVO vo = new TaskVO();
        vo.setRunClass("com.dianping.activity.remindnote.root.Root");
        vo.setRunMethod("init");
        vo.setRunParameter("");

//        vo.setRunClass("com.dianping.activityjob.thirdparty.xishiqu.XishiquBaseSynHandler");
//        vo.setRunMethod("handler");
//        vo.setRunParameter("");
        // vo.setRunClass("com.dianping.activityjob.thirdparty.xishiqu.XishiquTicketSynHandler");

        Class<?> aClass = helper.createClass(vo);
        System.out.println(aClass.getName());
        logger.info("class name :" + aClass.getName());

        // Field[] declaredFields = aClass.getDeclaredFields();
        // for (Field declaredField : declaredFields) {
        // System.out.println(declaredField.getName());
        // }
        //
        // Method[] declaredMethods = aClass.getDeclaredMethods();
        // for (Method declaredMethod : declaredMethods) {
        // System.out.println(declaredMethod.getName());
        // }

        invoke(aClass);

        // testN();

        // testY();

    }

    private static void invoke(Class<?> aClass) throws NoSuchMethodException, InstantiationException,
                                               IllegalAccessException, InvocationTargetException {
        Method method = aClass.getDeclaredMethod("execute", JobExecutionContext.class);

        Object o = aClass.newInstance();
        method.invoke(o, new MethodInvokingJob.TTest());
    }

    public static void testY() throws CannotCompileException, NotFoundException, IllegalAccessException,
                              InstantiationException, InvocationTargetException, NoSuchMethodException {
        ClassPool cp = ClassPool.getDefault();
        CtClass ctClass = cp.makeClass("com.slovef.JavassistClass");

        StringBuffer body = null;
        // 参数 1：属性类型 2：属性名称 3：所属类CtClass
        CtField ctField = new CtField(cp.get("java.lang.String"), "name", ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        // 设置name属性的get set方法
        ctClass.addMethod(CtNewMethod.setter("setName", ctField));
        ctClass.addMethod(CtNewMethod.getter("getName", ctField));
        ctClass.addField(ctField, CtField.Initializer.constant("default"));

        // 参数 1：参数类型 2：所属类CtClass
        CtConstructor ctConstructor = new CtConstructor(new CtClass[] {}, ctClass);
        body = new StringBuffer();
        body.append("{\n name=\"me\";\n}");
        ctConstructor.setBody(body.toString());
        ctClass.addConstructor(ctConstructor);

        // 参数： 1：返回类型 2：方法名称 3：传入参数类型 4：所属类CtClass
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[] {}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        body = new StringBuffer();
        body.append("{\n System.out.println(name);");
        body.append("\n System.out.println(\"execute ok\");");
        body.append("\n return ;");
        body.append("\n}");
        ctMethod.setBody(body.toString());
        ctClass.addMethod(ctMethod);
        Class<?> c = ctClass.toClass();

        Object o = c.newInstance();
        Method method = o.getClass().getMethod("execute", new Class[] {});
        // 调用字节码生成类的execute方法
        method.invoke(o, new Object[] {});
    }

    private static void testN() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("com.dianping.swiftly.core.BO.TaskBO");
        CtField f = new CtField(pool.get(STRING), TARGET_CLASS_NAME, ctClass);
        f.setModifiers(Modifier.PRIVATE);

        ctClass.addField(f, CtField.Initializer.constant("default"));

        CtField[] declaredFields = ctClass.getDeclaredFields();
        for (CtField declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
        ctClass.writeFile();

        Class aClass = ctClass.toClass();

        Object o = aClass.newInstance();
        aClass.getDeclaredMethod("");

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("new:" + field.getName());
        }
    }
}
