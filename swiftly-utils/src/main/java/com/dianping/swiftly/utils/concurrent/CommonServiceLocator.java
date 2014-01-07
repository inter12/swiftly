package com.dianping.swiftly.utils.concurrent;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.dianping.swiftly.utils.component.SpringObjLocator;

public class CommonServiceLocator extends SpringObjLocator {

    public static ThreadPoolTaskExecutor getThreadPools() {
        return getBean("CommonThreadPool");
    }

}
