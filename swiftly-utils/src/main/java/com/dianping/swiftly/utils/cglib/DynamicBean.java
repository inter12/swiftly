package com.dianping.swiftly.utils.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午2:05
 * 
 * </pre>
 */
public class DynamicBean {

    private Object  object;

    private BeanMap beanMap;

    public void setValue(String key, Object value) {
        beanMap.put(key, value);
    }

    public Object getValue(String key) {
        return beanMap.get(key);
    }

    public DynamicBean(Map<String, Class> map) {

        if (map == null || map.size() == 0) {
            return;
        }

        BeanGenerator beanGenerator = new BeanGenerator();

        for (Map.Entry<String, Class> entry : map.entrySet()) {
            beanGenerator.addProperty(entry.getKey(), entry.getValue());
        }

        object = beanGenerator.create();

        this.beanMap = BeanMap.create(object);
    }

    public Object getObject() {
        return object;
    }

    public static void main(String[] args) {
        DynamicBean.createObject();

    }

    private static void createObject() {
        Map<String, Class> properties = new HashMap<String, Class>(5);
        properties.put("intValue", int.class);
        properties.put("integer", Integer.class);
        properties.put("strValue", String.class);
        DynamicBean bean = new DynamicBean(properties);
        bean.setValue("intValue", 1);
        bean.setValue("integer", new Integer(2000));
        bean.setValue("strValue", "hehe");
        Object object1 = bean.getObject();

        System.out.println("------- intValue:" + bean.getValue("intValue"));
        System.out.println("------- integer:" + bean.getValue("integer"));
        System.out.println("------- strValue:" + bean.getValue("strValue"));

        Method[] declaredMethods = object1.getClass().getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            System.out.println("----- method name :" + declaredMethod.getName());
        }
    }
}
