package com.dianping.swiftly.api.enums;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 上午11:26
 *   作业运行的状态
 * </pre>
 */
public enum StatusEnum {

    READY(0, "准备状态"),

    RUNNING(1, "运行状态"),

    SUSPEND(2, "阻塞状态"),

    SUCCESS(3, "运行成功状态"),

    KILLED(4, "杀死状态"),

    DELETED(5, "删除状态"),

    FAILED(6, "运行失败");

    StatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private int    status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
