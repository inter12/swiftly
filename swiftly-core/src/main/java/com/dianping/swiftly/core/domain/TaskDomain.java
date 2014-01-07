package com.dianping.swiftly.core.domain;

import com.dianping.swiftly.api.vo.TaskVO;
import com.dianping.swiftly.core.bo.ObjectCopier;
import com.dianping.swiftly.core.entity.JobEntity;
import com.dianping.swiftly.core.Repository.dao.MySqlJobRepository;
import com.dianping.swiftly.core.component.RepositoryLocator;
import com.dianping.swiftly.core.vo.JobInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午6:19
 * 
 * </pre>
 */
public class TaskDomain {

    private static MySqlJobRepository jobRepository;

    private static Logger             LOGGER = LoggerFactory.getLogger(JobDomain.class);

    private Scheduler                 scheduler;

    public TaskDomain(Scheduler scheduler) {
        this.scheduler = scheduler;

    }

    public List<TaskVO> loadAllTask() {

        List<JobEntity> jobEntities = jobRepository.loadAllEntities();

        if (CollectionUtils.isEmpty(jobEntities)) {
            return Collections.emptyList();
        }

        List<TaskVO> taskVOList = new ArrayList<TaskVO>(jobEntities.size());
        for (JobEntity jobEntity : jobEntities) {
            taskVOList.add(ObjectCopier.entity2VO(jobEntity));
        }

        return taskVOList;

    }

    public void initScheduler() {

        List<TaskVO> taskVOList = loadAllTask();

        if (CollectionUtils.isEmpty(taskVOList)) {
            return;
        }

        Assert.notNull(scheduler, "scheduler should not be null!");

        for (TaskVO taskVO : taskVOList) {

            JobDomain jobDomain = new JobDomain(taskVO);
            JobDomain.setJobRepository(RepositoryLocator.getJobRepository());

            try {
                JobInfoVO jobInfoVO = jobDomain.assemblyVO();
                scheduler.scheduleJob(jobInfoVO.getJobDetail(), jobInfoVO.getTrigger());
            } catch (Exception e) {
                LOGGER.error("add task to scheduler error! task name " + taskVO.getName(), e);
            }
        }

        LOGGER.info(" ---------------- total task size is:" + taskVOList.size() + "----------------");

    }

    public static void setJobRepository(MySqlJobRepository jobRepository) {
        TaskDomain.jobRepository = jobRepository;
    }
}
