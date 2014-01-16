package com.dianping.swiftly.utils.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaoming on 14-1-15.
 * 
 * <pre>
 * 最简单的并发安全的local cache
 * </pre>
 */
public class Cache {

    private int                                     maxSize = 100;

    private final ConcurrentHashMap<Object, Object> map;

    private CacheLoader                             cacheLoader;

    public Cache() {

        map = new ConcurrentHashMap<Object, Object>(maxSize);
    }

    public Object get(String key) {
        Object result = createChacheEntry(key);
        removeOldestCacheEntryIfNecessary();
        return result;
    }

    private Object createChacheEntry(Object key) {
        Object result = map.get(key);
        if (result == null) {
            Object putResult = map.putIfAbsent(key, cacheLoader.loadCache());
            if (putResult != null) {
                result = putResult;
            }
        }
        return result;
    }

    private void removeOldestCacheEntryIfNecessary() {
        if (map.size() > maxSize) {
            Object keyToDelete = map.keys().nextElement(); // very effective ;)
            map.remove(keyToDelete);
        }
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setCacheLoader(CacheLoader cacheLoader) {
        this.cacheLoader = cacheLoader;
    }
}
