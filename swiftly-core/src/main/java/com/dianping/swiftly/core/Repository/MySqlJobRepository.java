package com.dianping.swiftly.core.Repository;

import com.dianping.swiftly.core.Entity.JobEntity;

import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:19
 * 
 *  MYSQL实现的存储
 * </pre>
 */
public interface MySqlJobRepository {

    public List<JobEntity> loadAllEntities();

    public JobEntity loadEntity(int jobId);
}
