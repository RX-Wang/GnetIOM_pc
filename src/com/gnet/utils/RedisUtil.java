package com.gnet.utils;

import java.io.Serializable;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <table width="100%" border="1px">
 * <tr>
 * <td width="20%">作者</td><td width="80%" colspan="2">sam</td>
 * </tr>
 * <tr>
 * <td width="20%">版本</td><td width="80%" colspan="2">v1.0</td>
 * </tr>
 * <tr>
 * <td width="20%">创建日期</td><td width="80%" colspan="2">2013-06-24</td>
 * </tr>
 * <tr>
 * <td width="100%" colspan="3">修订记录:</td>
 * <tr>
 * <td width="20%">修改日期</td><td width="20%">修改人</td><td width="60%">修改记录</td>
 * </tr>
 * <tr>
 * <td width="20%">-------</td><td width="20%">-------</td><td width="60%">--------------</td>
 * </tr>
 * <tr>
 * <td width="20%">描述信息</td><td width="80%" colspan="2">操作redis的工具类</td>
 * </tr>
 * </tr>
 * </table>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisUtil {

	static RedisTemplate<Serializable, Serializable> redisTemplate = null;
	
	static{
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
		redisTemplate = (RedisTemplate) app.getBean("redisTemplate");
	}
	
	/**
     * 描述信息:添加缓存
     * 
     * @param key
     * 				添加key
     * @param value
     * 				添加value
     * @param liveTime
     * 				设置存活时间(byte),单位秒
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 描述信息:添加缓存
     * 
     * @param key
     * 				添加key
     * @param value
     * 				添加value
     * @param liveTime
     * 				设置存活时间(byte),单位秒
     */
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * 描述信息:添加缓存
     * 
     * @param key
     * 				添加key
     * @param value
     * 				添加value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * 描述信息:添加缓存
     * 
     * @param key
     * 				添加key
     * @param value
     * 				添加value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * 描述信息:通过key删除
     * 
     * @param keys
     * 				标识
     */
	public Long del(final String... keys) {
        return (Long)redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    /**
     * 描述信息:获取缓存
     * 
     * @param key
     * 				标识
     * @return
     * 				缓存数据
     */
    public String get(final String key) {
        return (String)redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    /**
     * 描述信息:通过正则匹配keys
     * 
     * @param pattern
     * 				正则表达式
     * @return 
     * 				缓存数据
     */
    public Set<Serializable> keys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    /**
     * 描述信息:检查key是否已经存在
     * 
     * @param key
     * 				标识
     * @return
     * 				缓存是否存在
     */
    public Boolean exists(final String key) {
        return (Boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * 描述信息:清空缓存
     * 
     * @return
     * 				操作的状态
     */
    public String flushDB() {
        return (String)redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * 描述信息:查看redis内的缓存数量
     * 
     * @return
     * 				缓存数量
     */
    public Long dbSize() {
        return (Long)redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * 描述信息:检查是否连接成功
     * 
     * @return
     * 				是否连接成功
     */
    public String ping() {
        return (String)redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

}
