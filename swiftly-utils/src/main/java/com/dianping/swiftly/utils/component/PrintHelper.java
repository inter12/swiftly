package com.dianping.swiftly.utils.component;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 下午2:06
 *   打印工具类
 * </pre>
 */
public class PrintHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(PrintHelper.class);

    static {
        LoggerHelper.initLog();
    }

    public static void print(Object o) {
        Assert.notNull(o, "object is null!");

        boolean useToStringMethod = false;

        if (Collection.class.isAssignableFrom(o.getClass())) {
            iterator(o, useToStringMethod);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            Map map = (Map) o;
            Collection values = map.values();
            iterator(values, useToStringMethod);
        } else {
            printObject(o, useToStringMethod);
        }

    }

    public static void print(Object o, boolean useToStringMethod) {
        Assert.notNull(o, "object is null!");

        if (Collection.class.isAssignableFrom(o.getClass())) {

            iterator(o, useToStringMethod);

        } else if (Map.class.isAssignableFrom(o.getClass())) {

            Map map = (Map) o;
            Collection values = map.values();
            iterator(values, useToStringMethod);

        } else {

            printObject(o, useToStringMethod);
        }

    }

    private static void printObject(Object o, boolean useToStringMethod) {
        Assert.notNull(o, "object is null!");

        if (useToStringMethod) {
            LOGGER.info("toString:" + o.toString());
        } else {
            printByReflect(o);
        }

    }

    private static void printByReflect(Object o) {
        Assert.notNull(o, "object is null!");

        Class<?> aClass = o.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if (ArrayUtils.isEmpty(declaredFields)) {
            return;
        }

        LOGGER.info("start------------------");
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String name = field.getName();
            String value = null;
            try {
                value = field.get(o) == null ? "" : field.get(o).toString();
            } catch (IllegalAccessException e) {
                LOGGER.error("get field error . name :" + name);
            }

            LOGGER.info("field:" + name + " value:" + value);
        }
        LOGGER.info("end--------------------");

    }

    private static void iterator(Object o, boolean useToStringMethod) {
        Assert.notNull(o, "object is null!");

        Collection collection = (Collection) o;

        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            printObject(next, useToStringMethod);
        }
    }
}
