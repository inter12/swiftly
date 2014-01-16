package com.dianping.swiftly.utils.component;

import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <pre>
 *  Created with IntelliJ IDEA.
 *  User: zhaoming
 *  Date: 13-11-6
 *  Time: 下午4:33
 *  对于spring ResourceUtils 的扩展
 * </pre>
 */
public class ResourceUtilsExtended extends ResourceUtils {

    /**
     * 根据路径将文件转化为properties格式
     * 
     * @param path 文件路径
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String path) throws IOException {
        Assert.notNull(path, "Resource location must not be null");

        InputStream inputStream = ResourceUtils.getURL(path).openStream();
        Properties properties = new Properties();
        properties.load(inputStream);

        Assert.notNull(properties, "properties shoulid not be null!");
        return properties;
    }
}
