package github.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static JedisUtils js;
    private static JedisPool pool;

    private JedisUtils() {
    }

    public static JedisUtils getInstance() {
        if (js == null) {
            synchronized (JedisUtils.class) {
                if (js == null) {
                    js = new JedisUtils();
                }
            }
        }
        return js;
    }

    /**
     * 初始化jedispool
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(6000);
            config.setMaxIdle(2000);
            config.setMaxWaitMillis(-1);
            config.setMinEvictableIdleTimeMillis(1800000);
            config.setSoftMinEvictableIdleTimeMillis(1800000);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, ResourcesUtils.redisip(), Integer.valueOf(ResourcesUtils.reidsport()),
                    10000, ResourcesUtils.redispwd());
//            pool = new JedisPool(config, "172.16.31.103", 6379, 10000, "wang");
//            pool=new JedisPool(config,"127.0.0.1", 6379, 10000, "1234");
        } catch (Exception e) {
            logger.info("exception:" + e.getMessage());
            pool = null;
        }

    }

    /**
     * 获取redis资源
     *
     * @return
     */
    public synchronized static Jedis getResource() {
        try {
            if (pool != null) {
                Jedis resource = pool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放redis资源
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 根据key获取值
     *
     * @param key
     */
    public Set<String> keys(String key) {
        Jedis jedis = getResource();
        Set<String> set = null;
        if (jedis != null) {
            set = jedis.keys(key);
            returnResource(jedis);
        }
        return set;
    }

    /**
     * String数据结构的插入
     */


    public void set(String key, String value) {
        Jedis jedis = getResource();
        jedis.set(key, value);
        returnResource(jedis);
    }

    public void setSession(String key, Session session) {
        Jedis jedis = getResource();
        jedis.set(key, serialize(session));
        returnResource(jedis);
    }

    public void del(String key) {
        Jedis jedis = getResource();
        jedis.del(key);
        returnResource(jedis);
    }

    public Object getObject(String key) {
        Jedis jedis = getResource();
        Object obj = deserialize(jedis.get(key));
        returnResource(jedis);
        return obj;
    }

    public void set(String key, int time, String value) {
        Jedis jedis = getResource();
        jedis.setex(key, time, value);
        returnResource(jedis);
    }

    public String get(String key) {
        Jedis jedis = getResource();
        String value = jedis.get(key);
        returnResource(jedis);
        return value;
    }

    public boolean existKey(String key) {
        Jedis jedis = getResource();
        boolean flag = jedis.exists(key);
        returnResource(jedis);
        return flag;
    }

    public long incr(String key) {
        Jedis jedis = getResource();
        long value = 0;
        if (jedis != null) {
            value = jedis.incr(key);
            returnResource(jedis);
        }
        return value;
    }

    /**
     * @param key    key值
     * @param score  分数
     * @param member
     */
    public void zadd(final String key, final String score, final String member) {
        Jedis jedis = getResource();
        jedis.zadd(SafeEncoder.encode(key), Double.valueOf(score), SafeEncoder.encode(member));
        returnResource(jedis);
    }

    public Set<String> sortByDescLimit10(final String key) {
        Jedis jedis = getResource();
        Set<String> zrange = jedis.zrevrange(key, 0, 9);
        returnResource(jedis);
        return zrange;
    }

    /**
     * 向set数据结构插入数据
     *
     * @param key
     * @param value
     */

    public void setSetsadd(String key, String value) {
        Jedis jedis = getResource();
        jedis.sadd(key, value);
        returnResource(jedis);
    }

    public boolean existSetVlaue(String key, String value) {
        Jedis jedis = getResource();
        boolean flag = false;
        flag = jedis.sismember(key, value);
        returnResource(jedis);
        return flag;
    }


    public Set<String> getSetsValues(String key) {
        Jedis jedis = getResource();
        Set<String> values = jedis.smembers(key);
        returnResource(jedis);
        return values;
    }


    //hash操作

    public String mapadd(String key,Map<String,String> maps){
        Jedis jedis = getResource();
        String value = jedis.hmset(key,maps);
        logger.info("map add redis :"+value);
        returnResource(jedis);
        return value;
    }

    public List<String> hmget(String key,String mapskeys){
        Jedis jedis = getResource();
        List<String> value = jedis.hmget(key,mapskeys);
        returnResource(jedis);
        return value;
    }

    public long maplength(String key){
        Jedis jedis = getResource();
        long length = jedis.hlen(key);
        returnResource(jedis);
        return length;
    }

    public boolean mapexistkey(String key){
        Jedis jedis = getResource();
        boolean flag = jedis.exists(key);
        returnResource(jedis);
        return  flag;
    }

    public long existmapkey(String key,String mapKey){
        Jedis jedis = getResource();
        long flag = jedis.exists(key,mapKey);
        returnResource(jedis);
        return flag;
    }

    public Set<String> mapkeys(String key){
        Jedis jedis = getResource();
        Set<String> keys = jedis.hkeys(key);
        returnResource(jedis);
        return keys;
    }

    public List<String> mapValue(String key){
        Jedis jedis = getResource();
        List<String> values = jedis.hvals(key);
        returnResource(jedis);
        return  values;
    }

    public long mapdel(String key,String mapkey){
        Jedis jedis = getResource();
        long  flag = jedis.hdel(key,mapkey);
        returnResource(jedis);
        return  flag;
    }


//----------------------烦躁的分割线--------------------------

    public void expire(String key,int seconds){
        Jedis jedis = getResource();
        jedis.expire(key,seconds);
        returnResource(jedis);
    }




    public void setListValue(String key, String value) {
        Jedis jedis = getResource();
        jedis.rpush(key, value);
        returnResource(jedis);
    }

    /**
     * 删除某key
     *
     * @param key
     */
    public void del_string(String key) {
        Jedis jedis = getResource();
        if (jedis != null) {
//            if (jedis.exists(key)) {
            jedis.del(key);
//            }
            returnResource(jedis);
        }
    }


    private static Object deserialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        } finally {
            try {
                ois.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static String serialize(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        } finally {
            try {
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
