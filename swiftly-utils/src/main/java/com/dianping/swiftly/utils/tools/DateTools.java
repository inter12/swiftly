package com.dianping.swiftly.utils.tools;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.swiftly.utils.component.LoggerHelper;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 14-1-7
 *  Time: 上午11:36
 * 
 * </pre>
 */
public class DateTools {

    private static Logger      LOGGER              = LoggerFactory.getLogger(DateTools.class);
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public void initDate() {

        // 初始化一个对象
        DateTime date = new DateTime(2014, 1, 1, 12, 0, 0);
        String dateStr = date.toString(YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("date :" + dateStr);

        // 30天后的日期
        String newDateStr2 = date.plusDays(30).toString(YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("date :" + newDateStr2);

        // 30天后，当周的最后一天 ：星期日
        String maxdate = date.plusDays(30).dayOfWeek().withMaximumValue().toString(YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("date :" + maxdate);

        // 30天后，当周的第一天：星期一
        String mindate = date.plusDays(30).dayOfWeek().withMinimumValue().toString(YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("date :" + mindate);
    }

    public void format() {

        // 格式转化 方式一
        DateTimeFormatter formatter = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM_SS);
        String testDateStr = "2014-1-1 12:00:00";
        DateTime dateTime = formatter.parseDateTime(testDateStr);
        String result = dateTime.dayOfYear().withMaximumValue().toString(formatter);
        LOGGER.info("result:" + result);

        // 格式转化 方式二
        DateTime parse = DateTime.parse(testDateStr, formatter);
        LOGGER.info("result:" + parse.dayOfYear().withMaximumValue().toString(formatter));
    }

    public void compare() {
        DateTime dateTime1 = new DateTime(2014, 1, 1, 12, 00, 00);
        DateTime dateTime2 = new DateTime(2014, 1, 1, 12, 00, 01);

        // 时间之间的比较
        boolean after = dateTime2.isAfter(dateTime1);
        LOGGER.info("is after" + after);

        // 两个时间之间相差的秒数
        Seconds seconds = Seconds.secondsBetween(dateTime1, dateTime2);
        LOGGER.info(seconds.getSeconds() + "");

        LOGGER.info("1:" + dateTime1.toString(YYYY_MM_DD_HH_MM_SS));
        LOGGER.info("2:" + dateTime2.toString(YYYY_MM_DD_HH_MM_SS));

        // 两个时间之间xiang相差的天数
        Days days = Days.daysBetween(dateTime1, dateTime2);
        LOGGER.info("second:" + days.toStandardDuration().getStandardSeconds());

        LOGGER.info(days.get(DurationFieldType.hours()) + "");
        LOGGER.info(days.getFieldType().getName());
        LOGGER.info(days.getPeriodType().getName());
        LOGGER.info(days.toStandardSeconds().getSeconds() + "");
        LOGGER.info(days.getDays() + "");
    }

    public static void main(String[] args) {

        LoggerHelper.initLog();
        DateTools tools = new DateTools();
        // tools.initDate();
        tools.format();
        // tools.compare();
    }
}
