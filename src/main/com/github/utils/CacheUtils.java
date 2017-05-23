package github.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存工具
 * Created by wh on 2016/9/28.
 */
public class CacheUtils {

    private static CacheManager cacheManager = SpringContextHolder.getBean("ehcacheManager");

    private static final String SYS_CACHE = "sysCache";

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    /**
     * 获取一个cache
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache==null){
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    public static void remove(String cacehName,String key){
        getCache(cacehName).remove(key);
    }

    public static void put(String cacheName,String key,String value){
        Element element = new Element(key,value);
        getCache(cacheName).put(element);
    }

    public static void put(String sessionId, Object object){
        Element element = new Element(sessionId, object);
        getCache(SYS_CACHE).put(element);
    }

    public static Object get(String cacheName,String key){
        Element element = getCache(cacheName).get(key);
        return  element==null?null:element.getObjectValue();
    }

    /**
     *系统的默认缓存的操作
     */
    public static void remove(String key){
        remove(SYS_CACHE,key);
    }

    public static void put(String key,String value){
        put(SYS_CACHE,key,value);
    }

    public static Object get(String key){
        return get(SYS_CACHE,key);
    }

    public static void put(String key, int secends , Object value) {
        Element element = new Element(key, value);
        element.setTimeToLive(secends);
        getCache(SYS_CACHE).put(element);
    }
}
