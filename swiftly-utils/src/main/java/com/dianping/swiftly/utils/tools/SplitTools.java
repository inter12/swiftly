package com.dianping.swiftly.utils.tools;

import com.dianping.swiftly.utils.component.PrintHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-12-25
 *  Time: 下午6:48
 * 
 * </pre>
 */
public class SplitTools {

    public static <T> List<List<T>> splitUser2List(List<T> userIds, int count) {

        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }

        int cpuCount = count == 0 ? Runtime.getRuntime().availableProcessors() * 2 : count;
        int listSize = userIds.size() / cpuCount + 1;

        List<List<T>> result = new ArrayList<List<T>>(listSize + 1);
        for (int i = 0; i < cpuCount; i++) {
            if (i * listSize >= userIds.size()) {
                continue;
            }

            List<T> temp = null;
            if (i == cpuCount - 1) {
                temp = userIds.subList(i * listSize, userIds.size());
            } else {
                temp = userIds.subList(i * listSize, (i + 1) * listSize);
            }
            result.add(temp);
        }

        return result;

    }

    public static void main(String[] args) {
        List<Integer> testList = new ArrayList<Integer>(10);
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);

        List<List<Integer>> lists = SplitTools.splitUser2List(testList, 0);
        PrintHelper.print(lists);

    }

}
