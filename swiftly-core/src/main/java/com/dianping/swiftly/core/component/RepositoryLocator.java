package com.dianping.swiftly.core.component;

import com.dianping.swiftly.core.Repository.dao.MySqlJobRepository;
import com.dianping.swiftly.utils.component.SpringObjLocator;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-17
 *  Time: 下午9:06
 * 
 * </pre>
 */
public class RepositoryLocator extends SpringObjLocator {

    public static MySqlJobRepository getJobRepository() {
        return getBean("jobRepository");
    }

}
