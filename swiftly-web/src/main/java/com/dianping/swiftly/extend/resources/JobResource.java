package com.dianping.swiftly.extend.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dianping.swiftly.utils.component.AssertExtended;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午2:30
 *  针对JOB的操作
 * </pre>
 */
@Controller
@RequestMapping("/job")
public class JobResource {

    private static Logger      LOGGER      = LoggerFactory.getLogger(JobResource.class);

    public static final String RUN_ACTION  = "run";

    public static final String STOP_ACTION = "stop";

    /**
     * 针对JOB的操作 :停止或是启动
     */
    @RequestMapping(value = "/{jobId:[\\d]+}", method = RequestMethod.GET)
    public void actionJob(@RequestParam(value = "jobId", required = true)
    int jobId, @RequestParam(value = "action", required = true)
    String action) {

        // 1.parameter check
        AssertExtended.notZero(jobId, "[Assertion failed] - the jobId argument must not be zero");
        Assert.notNull(action, "[Assertion failed] - the action argument must not be zero");

        // 2. bussiness
        if (RUN_ACTION.equals(action)) {

        } else if (STOP_ACTION.equals(action)) {

        }

        // 3. unhandle exception
        LOGGER.error("invalid action name :" + action);
        throw new IllegalArgumentException("invalid action name :" + action);

    }

}
