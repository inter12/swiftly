package com.dianping.swiftly.core.BO;

import net.sf.cglib.beans.BeanCopier;

import com.dianping.swiftly.api.vo.TaskVO;
import com.dianping.swiftly.core.Entity.JobEntity;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-22
 *  Time: 上午11:24
 * 
 * </pre>
 */
public class ObjectCopier {

    private static BeanCopier beanCopier = BeanCopier.create(JobEntity.class, TaskVO.class, false);

    public static TaskVO entity2VO(JobEntity entity) {
        TaskVO taskVO = new TaskVO();
        beanCopier.copy(entity, taskVO, null);
        return taskVO;
    }

}
