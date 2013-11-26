package com.dianping.swiftly.core.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.dianping.swiftly.api.vo.TaskVO;
import com.dianping.swiftly.core.component.JavassistHelper;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-20
 *  Time: 下午6:07
 * 
 * </pre>
 */
public class MethodInvokeFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(MethodInvokeFactory.class);

    public static Class<?> getProxyClazz(ImplType type, TaskVO taskVO) throws Exception {

        Assert.notNull(type, "type should not be null!");
        Assert.notNull(taskVO, "taskVo should not be null!");

        LOGGER.debug("type:" + type.getType() + " desc:" + type.getDesc());

        Class<?> result = null;
        if (type.equals(ImplType.JAVASSIST)) {
            result = JavassistHelper.getInstance().createClass(taskVO);
        } else {
            throw new IllegalArgumentException("invalid type :" + type);
        }

        return result;
    }

    public enum ImplType {
        JAVASSIST(1, "javassist的实现!");

        private int    type;

        private String desc;

        ImplType(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        int getType() {
            return type;
        }

        void setType(int type) {
            this.type = type;
        }

        String getDesc() {
            return desc;
        }

        void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
