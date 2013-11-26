package com.dianping.swiftly.api.enums;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-18
 *  Time: 上午11:43
 * 
 * </pre>
 */
public enum RunTypeEnum {

    MAIN(1, "main函数运行"),

    SHELL(1, "shell脚本运行"),

    CLASS_METHOD(1, "类名+方法运行");

    RunTypeEnum(int type, String desc) {
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
