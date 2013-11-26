package com.dianping.swiftly.api.vo;

import java.io.Serializable;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-15
 *  Time: 下午2:34
 * 
 * </pre>
 */
public class TaskVO implements Serializable {

    private static final long serialVersionUID = 4351897798851138739L;

    private int               id;

    private String            owner;

    private String            deployMachineIP;

    private int               type;

    private String            name;

    private String            runClass;

    private String            runMethod;

    private String            runParameter;

    private String            runTrigger;

    private String            jobDesc;

    private int               maxRunTime;

    private int               maxWaitingTime;

    private int               retryTimes;

    private boolean           isKilledTimeout;

    private int               status;

    private String            jobGroup;

    private String            noticeEmail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDeployMachineIP() {
        return deployMachineIP;
    }

    public void setDeployMachineIP(String deployMachineIP) {
        this.deployMachineIP = deployMachineIP;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunClass() {
        return runClass;
    }

    public void setRunClass(String runClass) {
        this.runClass = runClass;
    }

    public String getRunMethod() {
        return runMethod;
    }

    public void setRunMethod(String runMethod) {
        this.runMethod = runMethod;
    }

    public String getRunParameter() {
        return runParameter;
    }

    public void setRunParameter(String runParameter) {
        this.runParameter = runParameter;
    }

    public String getRunTrigger() {
        return runTrigger;
    }

    public void setRunTrigger(String runTrigger) {
        this.runTrigger = runTrigger;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public int getMaxRunTime() {
        return maxRunTime;
    }

    public void setMaxRunTime(int maxRunTime) {
        this.maxRunTime = maxRunTime;
    }

    public int getMaxWaitingTime() {
        return maxWaitingTime;
    }

    public void setMaxWaitingTime(int maxWaitingTime) {
        this.maxWaitingTime = maxWaitingTime;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public boolean isKilledTimeout() {
        return isKilledTimeout;
    }

    public void setKilledTimeout(boolean killedTimeout) {
        isKilledTimeout = killedTimeout;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    @Override
    public String toString() {
        return "TaskVO{" + "id=" + id + ", owner='" + owner + '\'' + ", deployMachineIP='" + deployMachineIP + '\''
               + ", type=" + type + ", name='" + name + '\'' + ", runClass='" + runClass + '\'' + ", runMethod='"
               + runMethod + '\'' + ", runParameter='" + runParameter + '\'' + ", runTrigger='" + runTrigger + '\''
               + ", jobDesc='" + jobDesc + '\'' + ", maxRunTime=" + maxRunTime + ", maxWaitingTime=" + maxWaitingTime
               + ", retryTimes=" + retryTimes + ", isKilledTimeout=" + isKilledTimeout + ", status=" + status
               + ", jobGroup='" + jobGroup + '\'' + ", noticeEmail='" + noticeEmail + '\'' + '}';
    }
}
