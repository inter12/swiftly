package com.dianping.swiftly.extend.cglib;

import net.sf.cglib.beans.BulkBean;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-2
 *  Time: 下午2:58
 * 
 * </pre>
 */
public class BeanBulk {

    public static void main(String[] args) {
        BeanBulk beanBulk = new BeanBulk();

        beanBulk.test();
    }

    private void test() {
        String[] getter = new String[] { "getIntValue", "getIntegerValue", "getStrValue" };
        String[] setter = new String[] { "setIntValue", "setIntegerValue", "setStrValue" };
        Class[] classes = new Class[] { int.class, Integer.class, String.class };

        BulkBean bulkBean = BulkBean.create(BulkSource.class, getter, setter, classes);

        BulkSource bulkSource = new BulkSource();
        bulkSource.setIntValue(1);
        bulkSource.setIntegerValue(new Integer(2000));
        bulkSource.setStrValue("hehe");

        Object[] propertyValues = bulkBean.getPropertyValues(bulkSource);
        for (Object propertyValue : propertyValues) {
            System.out.println("---- value:" + propertyValue.toString());
        }
    }

    class BulkSource {

        private int     intValue;

        private Integer integerValue;

        private String  strValue;

        int getIntValue() {
            return intValue;
        }

        void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        Integer getIntegerValue() {
            return integerValue;
        }

        void setIntegerValue(Integer integerValue) {
            this.integerValue = integerValue;
        }

        String getStrValue() {
            return strValue;
        }

        void setStrValue(String strValue) {
            this.strValue = strValue;
        }
    }
}
