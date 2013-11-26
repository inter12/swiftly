/**
 * Project: social-biz File Created at 2011-6-10 $Id$ Copyright 2010 dianping.com. All rights reserved. This software is
 * the confidential and proprietary information of Dianping Company. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 * you entered into with dianping.com.
 */
package config.spring.test;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * 测试基类
 * 
 * @author zhaoming.xue
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/spring/spring-*.xml" })
@Ignore
public abstract class AbstractTest {

    public void notNull(Object obj) {
        assertNotNull(obj);
    }

    public void isNull(Object obj) {
        assertNull(obj);
    }

    public void equal(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    protected void printObject(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        System.out.println(o.toString());
    }

    protected void print(Object o) {
        if (o == null) {
            System.out.println("the result is null!");
            return;
        }

        if (Collection.class.isAssignableFrom(o.getClass())) {
            iterator(o);
        } else if (Map.class.isAssignableFrom(o.getClass())) {
            Map map = (Map) o;
            Collection values = map.values();
            iterator(values);
        } else {

            printObject(o);
        }

    }

    private void iterator(Object o) {
        Collection collection = (Collection) o;

        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            printObject(o);
        }
    }

}
