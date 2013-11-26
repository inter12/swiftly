package com.dianping.swiftly.webapp.resources;

import com.dianping.swiftly.utils.component.AssertExtended;
import com.dianping.swiftly.webapp.VO.JobLogVO;
import com.dianping.swiftly.api.vo.TaskVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午3:00
 *  针对任务的操作
 * </pre>
 */

@Controller
@RequestMapping("/")
public class Task {

    private static Logger LOGGER = LoggerFactory.getLogger(Task.class);

    /**
     * 加载所有可见的task信息
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskVO> index() {

        // TODO 加载所有的TASK
        return null;
    }

    /**
     * 新增TASK
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public void addTask() {
        // TODO post的数据请求还未定
    }

    /**
     * 删除TASK
     * 
     * @param taskId
     */
    @RequestMapping(value = "/task/{taskId:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteTask(@RequestParam(value = "taskId", required = true)
    int taskId) {

        AssertExtended.notZero(taskId, "[Assertion failed] - the taskId argument must not be zero");

        // TODO 删除任务
    }

    /**
     * 更新TASK
     * 
     * @param taskId
     */
    @RequestMapping(value = "/task/{taskId:[\\d]+}", method = RequestMethod.POST)
    public void updateTask(@RequestParam(value = "taskId", required = true)
    int taskId) {

        AssertExtended.notZero(taskId, "[Assertion failed] - the taskId argument must not be zero");

        // TODO 更新任务的详细信息

    }

    /**
     * 查看TASK详细信息
     * 
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/task/{taskId:[\\d]+}", method = RequestMethod.GET)
    public TaskVO jobDetail(@RequestParam(value = "taskId", required = true)
    int taskId) {

        AssertExtended.notZero(taskId, "[Assertion failed] - the taskId argument must not be zero");

        // TODO 查看任务的详细信息
        return null;
    }

    /**
     * 获取task运行的历史日志信息
     * 
     * @return
     */
    @RequestMapping(value = "/task/{taskId:[\\d]+}/log", method = RequestMethod.GET)
    public List<JobLogVO> findTaskRunHistory(@RequestParam(value = "taskId", required = true)
    int taskId) {

        AssertExtended.notZero(taskId, "[Assertion failed] - the taskId argument must not be zero");

        // TODO 查看任务的所有日志信息
        return null;
    }

    @RequestMapping(value = "/task/{taskId:[\\d]+}/log/{logId:[\\d]+}", method = RequestMethod.GET)
    public List<JobLogVO> findTaskRunHistory(@RequestParam(value = "taskId", required = true)
    int taskId, @RequestParam(value = "logId", required = true)
    int logId) {

        AssertExtended.notZero(taskId, "[Assertion failed] - the taskId argument must not be zero");
        AssertExtended.notZero(logId, "[Assertion failed] - the logId argument must not be zero");

        // TODO 查看任务的所有日志信息
        return null;
    }

}
