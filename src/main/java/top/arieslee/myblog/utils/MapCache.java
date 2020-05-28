package top.arieslee.myblog.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MapCache
 * @Description 自定义缓存
 * @Author Aries
 * @Date 2019/5/10 9:10
 * @Version 1.0
 **/
public class MapCache {

    //初始最大缓存数量
    private static final int MAX_CACHE_COUNT = 1024;

    //懒汉单例
    private static final MapCache INT = new MapCache();

    public static MapCache getCachePool() {
        return INT;
    }

    //缓存map实例
    private Map<String, CacheObject> cachePool;

    private MapCache() {
        this.cachePool = new ConcurrentHashMap<>(MAX_CACHE_COUNT);
    }

    public MapCache(int cacheCount) {
        cachePool = new ConcurrentHashMap<>(cacheCount);
    }

    //获取hash缓存
    public <T> T get(String key, String... fields) {
        for (String field : fields) {
            key = key + ":" + field;
        }
        return this.get(key);
    }

    //获取普通缓存
    public <T> T get(String key) {
        CacheObject cacheObject = cachePool.get(key);
        if (cacheObject != null) {
            long currentTime = System.currentTimeMillis() / 1000;
            //检验缓存是否过期
            if (cacheObject.getExpired() < 0 || cacheObject.getExpired() > currentTime) {
                //缓存在期限内
                return (T) cacheObject.getValue();
            } else {
                //缓存已过期，清理
                cachePool.remove(key);
            }
        }
        return null;
    }

    /**
     * @Description 建立普通缓存
     **/
    public void set(String key, Object value) {
        this.set(key, value, -1L);
    }

    /**
     * @Description 建立带过期时间的缓存
     **/
    public void set(String key, Object value, long expired) {
        expired = expired >= 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }

    /**
     * @return void
     * @Description 建立普通Hash缓存
     * @Param [key：键, fields：参数集, value：缓存值]
     **/
    public void set(String key, Object value, String... fields) {
        this.set(key, value, -1, fields);
    }

    /**
     * @return void
     * @Description 建立带过期时间的含参缓存
     * @Param [key：Hash键, fields：Hash值, value：缓存值, expired：过期时间]
     **/
    public void set(String key, Object value, long expired, String... fields) {
        for (String field : fields) {
            key = key + ":" + field;
        }
        expired = expired >= 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }

    /**
     * 设置一个hash缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     * @param value 缓存value
     */
    public void hset(String key, String field, Object value) {
        this.hset(key, field, value, -1);
    }

    /**
     * 设置一个hash缓存并带过期时间
     *
     * @param key     缓存key
     * @param field   缓存field
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    public void hset(String key, String field, Object value, long expired) {
        key = key + ":" + field;
        expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }

    //删除指定hash缓存
    public void deleteCache(String key, String field) {
        key = key + ":" + field;
        this.deleteCache(key);
    }

    //删除普通缓存
    public void deleteCache(String key) {
        cachePool.remove(key);
    }

    //清空缓存池
    public void clearCachePool() {
        this.cachePool.clear();
    }

    private static class CacheObject {
        private String key;
        private Object value;
        /**
         * @Description 过期时间,-1表示永久有效
         **/
        private long expired;

        CacheObject(String key, Object value, long expired) {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getExpired() {
            return expired;
        }
    }
}
