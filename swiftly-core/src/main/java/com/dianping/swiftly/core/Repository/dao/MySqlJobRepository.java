package com.dianping.swiftly.core.Repository.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.swiftly.core.entity.JobEntity;

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
public interface MySqlJobRepository extends GenericDao{

    @DAOAction(action = DAOActionType.QUERY)
    public List<JobEntity> loadAllEntities();
}
