package com.dianping.swiftly.utils.cache;

/**
 * Created by zhaoming on 14-1-16.
 */
public class CacheBuilder {

    private Cache cache;

    private CacheBuilder() {
    }

    public static CacheBuilder newCacheBuilder() {
        return new CacheBuilder();
    }

    public CacheBuilder withMaxSize(int maxSize) {
        cache.setMaxSize(maxSize);
        return this;
    }

    public Cache build(CacheLoader cacheLoader) {
        cache.setCacheLoader(cacheLoader);
        return cache;
    }

}
