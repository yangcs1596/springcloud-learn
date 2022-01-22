package com.safedog.common.util;

import com.safedog.common.redis.JedisPoolBuild;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;
import redis.clients.jedis.util.SafeEncoder;

import java.util.*;

/**
 * Redis工具类
 *
 * @author hcl on 2017/11/16.
 */
@Slf4j
public class CommonRedisUtils {
    /**
     * redis部署模式：0-单机模式，1-哨兵模式
     */
    public static final int REDIS_RUN_MODE_SENTINEL = 1;

    /**
     * 默认缓存时间
     */
    private static final int EXPIRE = 60000;

    private Properties properties;
    /**
     * 单机模式
     */
    private volatile static JedisPool singlePool = null;
    /**
     * 哨兵模式
     */
    private volatile static JedisSentinelPool sentinelPool = null;
    /**
     * redis工作模式 true 哨兵模式
     */
    protected volatile boolean sentinelMode = false;

    /**
     * 对Keys,以及存储结构为String、List、Set、HashMap类型的操作
     */
    private final Keys keys = new Keys();
    private final Strings strings = new Strings();
    private final Lists lists = new Lists();
    private final Sets sets = new Sets();
    private final Hash hash = new Hash();
    private final SortSet sortset = new SortSet();
    private final int REPLAY_SIZE = 5;

    public CommonRedisUtils() {
    }

//===========================================================

    /**
     * 初始化方法
     *
     * @param properties
     * @param sentinelMode
     */
    protected void initJedis(Properties properties, boolean sentinelMode) {
        this.sentinelMode = sentinelMode;
        this.properties = properties;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (sentinelMode) {
            //哨兵模式
            if (sentinelPool == null) {
                try {
                    JedisPoolBuild jedisPoolBuild = new JedisPoolBuild();
                    sentinelPool = jedisPoolBuild.initJedisSentinelPool(properties);
                    log.info("JedisSentinelPool init success！");
                } catch (Exception e) {
                    log.error("JedisSentinelPool init error！", e);
                }
            }
        } else {
            //单机模式
            if (singlePool == null) {
                try {
                    JedisPoolBuild jedisPoolBuild = new JedisPoolBuild();
                    singlePool = jedisPoolBuild.initJedisPool(properties);
                    log.info("JedisPool init success！");
                } catch (Exception e) {
                    log.error("initJedisPool error", e);
                }
            }
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public Jedis getJedis() {
        if (sentinelMode) {
            return sentinelPool.getResource();
        }
        return singlePool.getResource();
    }

    /**
     * 兼容性方法
     *
     * @return
     */
    @Deprecated
    public Jedis getMasterJedis() {
        return getJedis();
    }

    /**
     * 兼容性方法
     *
     * @return
     */
    @Deprecated
    public List<Jedis> getAllJedis() {
        List<Jedis> list = new ArrayList<>();
        Jedis jedis = getJedis();
        list.add(jedis);
        return list;
    }

    /**
     * 兼容性方法
     *
     * @return
     */
    @Deprecated
    public Jedis getJedis(Object obj) {
        return getJedis();
    }

    /**
     * 通用方法：释放Jedis
     *
     * @param jedis
     */
    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public Keys keys() {
        return keys;
    }

    public Strings strings() {
        return strings;
    }

    public Lists lists() {
        return lists;
    }

    public Sets sets() {
        return sets;
    }

    public Hash hash() {
        return hash;
    }

    public SortSet sortSet() {
        return sortset;
    }
    //===========================================================

    /**
     * Redis-cell 漏桶
     *
     * @param bucketName
     * @param maxBurst
     * @param timeFre
     * @param timeUnit
     * @return
     */
    public long clthrottle(String bucketName, long maxBurst, long timeFre, int timeUnit, int curSize) {
        Jedis jedis = getJedis();
        try {
            Client client = jedis.getClient();
            jedis.getClient().sendCommand(CellCommand.CLTHROTTLE, bucketName, maxBurst + "", timeFre + "", timeUnit + "", curSize + "");

            List<Long> replay = client.getIntegerMultiBulkReply();

            if (CollectionUtil.isNotEmpty(replay) && replay.size() == REPLAY_SIZE) {
                if (replay.get(0) == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                log.info("redis-cell {},return value is wrong!", bucketName);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }

        return 0;
    }

    /*******************************************Keys*******************************************/
    public class Keys {

        /**
         * 设置过期时间
         *
         * @param key
         * @param seconds
         * @return 返回影响的记录数
         */
        public long expire(String key, int seconds) {
            if (seconds <= 0) {
                log.warn("ERR invalid expire time in setex,seconds==>60");
                seconds = 60;
            }
            Jedis jedis = getJedis();
            try {
                long result = jedis.expire(key, seconds);
                return result;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 设置过期时间，默认值为60000seconds
         *
         * @param key
         */
        public long expire(String key) {
            return expire(key, EXPIRE);
        }

        /**
         * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
         *
         * @param key
         * @param timestamp 秒
         * @return 影响的记录数
         */
        public long expireAt(String key, long timestamp) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.expireAt(key, timestamp);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 查询key的过期时间
         *
         * @param key
         * @return 以秒为单位的时间表示
         */
        public long ttl(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long len = jedis.ttl(key);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 取消对key过期时间的设置
         *
         * @param key
         * @return 影响的记录数
         */
        public long persist(String key) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.persist(key);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 判断key是否存在
         *
         * @param key
         * @return boolean
         */
        public boolean exists(String key) {
            Jedis jedis = getJedis();
            try {
                boolean exis = jedis.exists(key);
                return exis;
            } finally {
                closeJedis(jedis);
            }
        }


        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param keys
         * @return 删除的记录数
         */
        public long del(String... keys) {
            Jedis jedis = getJedis();
            try {
                return jedis.del(keys);
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param keys
         * @return 删除的记录数
         */
        public long del(byte[]... keys) {
            Jedis jedis = getJedis();
            try {
                return jedis.del(keys);
            } finally {
                closeJedis(jedis);
            }
        }


        /**
         * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
         *
         * @param key
         * @return List<String> 集合的全部记录
         **/
        public List<String> sort(String key) {
            Jedis jedis = getJedis();
            try {
                List<String> list = jedis.sort(key);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 对List,Set,SortSet进行排序或limit
         *
         * @param key
         * @param parame 定义排序类型或limit的起止位置.
         * @return List<String> 全部或部分记录
         **/
        public List<String> sort(String key, SortingParams parame) {
            Jedis jedis = getJedis();
            try {
                List<String> list = jedis.sort(key, parame);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 返回指定key存储的类型
         *
         * @param key
         * @return String string|list|set|zset|hash
         **/
        public String type(String key) {
            Jedis jedis = getJedis();
            try {
                String type = jedis.type(key);
                return type;
            } finally {
                closeJedis(jedis);
            }
        }
    }

    /*******************************************Sets*******************************************/
    public class Sets {

        /**
         * 向Set添加一条记录，如果member已存在返回0,否则返回1
         *
         * @param key
         * @param member
         * @return 操作码, 0或1
         */
        public long sadd(String key, String member) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.sadd(key, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public long sadd(byte[] key, byte[] member) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.sadd(key, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 向set中添加多条记录
         *
         * @param key
         * @param members
         * @return
         */
        public long sadd(String key, String[] members) {
            Jedis jedis = getJedis();

            try {
                long s = jedis.sadd(key, members);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取给定key中元素个数
         *
         * @param key
         * @return 元素个数
         */
        public long scard(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long len = jedis.scard(key);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }


        /**
         * 确定一个给定的值是否存在
         *
         * @param key
         * @param member 要判断的值
         * @return 存在返回1，不存在返回0
         **/
        public boolean sismember(String key, String member) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                boolean s = jedis.sismember(key, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 返回集合中的所有成员
         *
         * @param key
         * @return 成员集合
         */
        public Set<String> smembers(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<String> set = jedis.smembers(key);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        public Set<byte[]> smembers(byte[] key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<byte[]> set = jedis.smembers(key);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 从集合中删除成员
         *
         * @param key
         * @return 被删除的成员
         */
        public String spop(String key) {
            Jedis jedis = getJedis();
            try {
                String s = jedis.spop(key);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 从集合中删除指定成员
         *
         * @param key
         * @param member 要删除的成员
         * @return 状态码，成功返回1，成员不存在返回0
         */
        public long srem(String key, String member) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.srem(key, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }
    }

    /*******************************************SortSet*******************************************/
    public class SortSet {

        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         *
         * @param key
         * @param score  权重
         * @param member 要加入的值，
         * @return 状态码 1成功，0已存在member的值
         */
        public long zadd(String key, double score, String member) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.zadd(key, score, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取集合中元素的数量
         *
         * @param key
         * @return 如果返回0则集合不存在
         */
        public long zcard(String key) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.zcard(key);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取指定权重区间内集合的数量
         *
         * @param key
         * @param min 最小排序位置
         * @param max 最大排序位置
         */
        public long zcount(String key, double min, double max) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.zcount(key, min, max);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获得set的长度
         *
         * @param key
         * @return
         */
        public long zlength(String key) {
            long len = 0;
            Set<String> set = zrange(key, 0, -1);
            len = set.size();
            return len;

        }

        /**
         * 权重增加给定值，如果给定的member已存在
         *
         * @param key
         * @param score  要增的权重
         * @param member 要插入的值
         * @return 增后的权重
         */
        public double zincrby(String key, double score, String member) {
            Jedis jedis = getJedis();
            try {
                double s = jedis.zincrby(key, score, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
         *
         * @param key
         * @param start 开始位置(包含)
         * @param end   结束位置(包含)
         * @return Set<String>
         */
        public Set<String> zrange(String key, int start, int end) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<String> set = jedis.zrange(key, start, end);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 返回指定权重区间的元素集合
         *
         * @param key
         * @param min 上限权重
         * @param max 下限权重
         * @return Set<String>
         */
        public Set<String> zrangeByScore(String key, double min, double max) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<String> set = jedis.zrangeByScore(key, min, max);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取指定值在集合中的位置，集合排序从低到高
         *
         * @param key
         * @param member
         * @return long 位置
         */
        public long zrank(String key, String member) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long index = jedis.zrank(key, member);
                return index;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取指定值在集合中的位置，集合排序从高到低
         *
         * @param key
         * @param member
         * @return long 位置
         */
        public long zrevrank(String key, String member) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long index = jedis.zrevrank(key, member);
                return index;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 从集合中删除成员
         *
         * @param key
         * @param member
         * @return 返回1成功
         */
        public long zrem(String key, String member) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.zrem(key, member);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除
         *
         * @param key
         * @return
         */
        public long zrem(String key) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.del(key);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除给定位置区间的元素
         *
         * @param key
         * @param start 开始区间，从0开始(包含)
         * @param end   结束区间,-1为最后一个元素(包含)
         * @return 删除的数量
         */
        public long zremrangeByRank(String key, int start, int end) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.zremrangeByRank(key, start, end);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除给定权重区间的元素
         *
         * @param key
         * @param min 下限权重(包含)
         * @param max 上限权重(包含)
         * @return 删除的数量
         */
        public long zremrangeByScore(String key, double min, double max) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.zremrangeByScore(key, min, max);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取给定区间的元素，原始按照权重由高到低排序
         *
         * @param key
         * @param start
         * @param end
         * @return Set<String>
         */
        public Set<String> zrevrange(String key, int start, int end) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<String> set = jedis.zrevrange(key, start, end);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取给定值在集合中的权重
         *
         * @param key
         * @param memebr
         * @return double 权重
         */
        public double zscore(String key, String memebr) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Double score = jedis.zscore(key, memebr);
                if (score != null) {
                    return score;
                }
                return 0;
            } finally {
                closeJedis(jedis);
            }
        }
    }

    /*******************************************Hash*******************************************/
    public class Hash {

        /**
         * 从hash中删除指定的存储
         *
         * @param key
         * @param fieid 存储的名字
         * @return 状态码，1成功，0失败
         */
        public long hdel(String key, String fieid) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.hdel(key, fieid);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public long hdel(String key) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.del(key);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 测试hash中指定的存储是否存在
         *
         * @param key
         * @param fieid 存储的名字
         * @return 1存在，0不存在
         */
        public boolean hexists(String key, String fieid) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                boolean s = jedis.hexists(key, fieid);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public boolean hexists(byte[] key, byte[] fieid) {
            Jedis sjedis = getJedis();

            try {
                boolean s = sjedis.hexists(key, fieid);
                return s;
            } finally {
                closeJedis(sjedis);
            }
        }

        public boolean hexists(String key, byte[] fieid) {
            return hexists(key.getBytes(), fieid);
        }


        /**
         * 返回hash中指定存储位置的值
         *
         * @param key
         * @param fieid 存储的名字
         * @return 存储对应的值
         */
        public String hget(String key, String fieid) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                String s = jedis.hget(key, fieid);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public byte[] hget(byte[] key, byte[] fieid) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();

            try {
                byte[] s = jedis.hget(key, fieid);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public byte[] hget(String key, byte[] fieid) {
            return hget(key.getBytes(), fieid);
        }

        /**
         * 以Map的形式返回hash中的存储和值
         *
         * @param key
         * @return Map<Strinig, String>
         */
        public Map<String, String> hgetAll(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Map<String, String> map = jedis.hgetAll(key);
                return map;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加一个对应关系
         *
         * @param key
         * @param fieid
         * @param value
         * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
         **/
        public long hset(String key, String fieid, String value) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.hset(key, fieid, value);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        public long hset(String key, String fieid, byte[] value) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.hset(key.getBytes(), fieid.getBytes(), value);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加对应关系，只有在fieid不存在时才执行
         *
         * @param key
         * @param fieid
         * @param value
         * @return 状态码 1成功，0失败fieid已存
         **/
        public long hsetnx(String key, String fieid, String value) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.hsetnx(key, fieid, value);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取hash中value的集合
         *
         * @param key
         * @return List<String>
         */
        public List<String> hvals(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                List<String> list = jedis.hvals(key);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
         *
         * @param key
         * @param fieid 存储位置
         * @param value 要增加的值,可以是负数
         * @return 增加指定数字后，存储位置的值
         */
        public long hincrby(String key, String fieid, long value) {
            Jedis jedis = getJedis();
            try {
                long s = jedis.hincrBy(key, fieid, value);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 返回指定hash中的所有存储名字,类似Map中的keySet方法
         *
         * @param key
         * @return Set<String> 存储名称的集合
         */
        public Set<String> hkeys(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                Set<String> set = jedis.hkeys(key);
                return set;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取hash中存储的个数，类似Map中size方法
         *
         * @param key
         * @return long 存储的个数
         */
        public long hlen(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long len = jedis.hlen(key);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         *
         * @param key
         * @param fieids 存储位置
         * @return List<String>
         */
        public List<String> hmget(String key, String... fieids) {
            Jedis jedis = getJedis();
            try {
                List<String> list = jedis.hmget(key, fieids);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        public List<byte[]> hmget(byte[] key, byte[]... fieids) {
            Jedis jedis = getJedis();
            List<byte[]> list = jedis.hmget(key, fieids);
            return list;
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param key
         * @param map 对应关系
         * @return 状态，成功返回OK
         */
        public String hmset(String key, Map<String, String> map) {
            Jedis jedis = getJedis();
            try {
                String s = jedis.hmset(key, map);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param key
         * @param map 对应关系
         * @return 状态，成功返回OK
         */
        public String hmset(byte[] key, Map<byte[], byte[]> map) {
            Jedis jedis = getJedis();
            try {
                String s = jedis.hmset(key, map);
                return s;
            } finally {
                closeJedis(jedis);
            }
        }

    }

    /*******************************************Strings*******************************************/
    public class Strings {
        /**
         * 根据key获取记录
         *
         * @param key
         * @return 值
         */
        public String get(String key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                String value = jedis.get(key);
                return value;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 根据key获取记录
         *
         * @param key
         * @return 值
         */
        public byte[] get(byte[] key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                byte[] value = jedis.get(key);
                return value;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key
         * @param seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(String key, int seconds, String value) {
            if (seconds <= 0) {
                log.warn("ERR invalid expire time in setex,seconds==>60");
                seconds = 60;
            }
            Jedis jedis = getJedis();
            try {
                String str = jedis.setex(key, seconds, value);
                return str;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key
         * @param seconds 过期时间，以秒为单位
         * @param value
         * @return String 操作状态
         */
        public String setEx(byte[] key, int seconds, byte[] value) {
            if (seconds <= 0) {
                log.warn("ERR invalid expire time in setex,seconds==>60");
                seconds = 60;
            }
            Jedis jedis = getJedis();
            try {
                String str = jedis.setex(key, seconds, value);
                return str;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加一条记录，仅当给定的key不存在时才插入
         *
         * @param key
         * @param value
         * @return long 状态码，1插入成功且key不存在，0未插入，key存在
         */
        public long setnx(String key, String value) {
            Jedis jedis = getJedis();
            try {
                long str = jedis.setnx(key, value);
                return str;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            try {
                String status = jedis.set(key, value);
                return status;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
         * 例:String str1="123456789";<br/>
         * 对str1操作后setRange(key,4,0000)，str1="123400009";
         *
         * @param key
         * @param offset
         * @param value
         * @return long value的长度
         */
        public long setRange(String key, long offset, String value) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.setrange(key, offset, value);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 在指定的key中追加value
         *
         * @param key
         * @param value
         * @return long 追加后value的长度
         **/
        public long append(String key, String value) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.append(key, value);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
         *
         * @param key
         * @param number 要减去的值
         * @return long 减指定值后的值
         */
        public long decrBy(String key, long number) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.decrBy(key, number);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * <b>可以作为获取唯一id的方法</b><br/>
         * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
         *
         * @param key
         * @param number 要减去的值
         * @return long 相加后的值
         */
        public long incrBy(String key, long number) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.incrBy(key, number);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 对指定key对应的value进行截取
         *
         * @param key
         * @param startOffset 开始位置(包含)
         * @param endOffset   结束位置(包含)
         * @return String 截取的值
         */
        public String getrange(String key, long startOffset, long endOffset) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                String value = jedis.getrange(key, startOffset, endOffset);
                return value;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取并设置指定key对应的value<br/>
         * 如果key存在返回之前的value,否则返回null
         *
         * @param key
         * @param value
         * @return String 原始value或null
         */
        public String getSet(String key, String value) {
            Jedis jedis = getJedis();
            try {
                String str = jedis.getSet(key, value);
                return str;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
         *
         * @param keys
         * @return List<String> 值得集合
         */
        public List<String> mget(String... keys) {
            Jedis jedis = getJedis();
            try {
                return jedis.mget(keys);
            } finally {
                closeJedis(jedis);
            }
        }


        /**
         * 获取key对应的值的长度
         *
         * @param key
         * @return value值得长度
         */
        public long strlen(String key) {
            Jedis jedis = getJedis();
            try {
                long len = jedis.strlen(key);
                return len;
            } finally {
                closeJedis(jedis);
            }
        }
    }

    /*******************************************Lists*******************************************/
    public class Lists {
        /**
         * List长度
         *
         * @param key
         * @return 长度
         */
        public long llen(String key) {
            return llen(SafeEncoder.encode(key));
        }

        /**
         * List长度
         *
         * @param key
         * @return 长度
         */
        public long llen(byte[] key) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                long count = jedis.llen(key);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @param value 值
         * @return 状态码
         */
        public String lset(byte[] key, int index, byte[] value) {
            Jedis jedis = getJedis();
            try {
                String status = jedis.lset(key, index, value);
                return status;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         *
         * @param
         * @param index 位置
         * @param value 值
         * @return 状态码
         */
        public String lset(String key, int index, String value) {
            return lset(SafeEncoder.encode(key), index,
                    SafeEncoder.encode(value));
        }

        /**
         * 获取List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @return 值
         **/
        public String lindex(String key, int index) {
            return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
        }

        /**
         * 获取List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @return 值
         **/
        public byte[] lindex(byte[] key, int index) {
            Jedis jedis = getJedis();
            try {
                byte[] value = jedis.lindex(key, index);
                return value;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 将List中的第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public String lpop(String key) {
            return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
        }

        /**
         * 将List中的第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public byte[] lpop(byte[] key) {
            Jedis jedis = getJedis();
            try {
                byte[] value = jedis.lpop(key);
                return value;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 将List中最后第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public String rpop(String key) {
            Jedis jedis = getJedis();
            String value = jedis.rpop(key);
            return value;
        }

        /**
         * 向List尾部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long lpush(String key, String value) {
            return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 向List头部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long rpush(String key, String value) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.rpush(key, value);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 向List头部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long rpush(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.rpush(key, value);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 向List中追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long lpush(byte[] key, byte[] value) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.lpush(key, value);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         *
         * @param key
         * @param start
         * @param end
         * @return List
         */
        public List<String> lrange(String key, long start, long end) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                List<String> list = jedis.lrange(key, start, end);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         *
         * @param key
         * @param start
         * @param end   如果为负数，则尾部开始计算
         * @return List
         */
        public List<byte[]> lrange(byte[] key, int start, int end) {
            //ShardedJedis jedis = getShardedJedis();
            Jedis jedis = getJedis();
            try {
                List<byte[]> list = jedis.lrange(key, start, end);
                return list;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         *
         * @param key
         * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public long lrem(byte[] key, int c, byte[] value) {
            Jedis jedis = getJedis();
            try {
                long count = jedis.lrem(key, c, value);
                return count;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         *
         * @param key
         * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public long lrem(String key, int c, String value) {
            return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         *
         * @param key
         * @param start 记录的开始位置(0表示第一条记录)
         * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         */
        public String ltrim(byte[] key, int start, int end) {
            Jedis jedis = getJedis();
            try {
                String str = jedis.ltrim(key, start, end);

                return str;
            } finally {
                closeJedis(jedis);
            }
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         *
         * @param key
         * @param start 记录的开始位置(0表示第一条记录)
         * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         */
        public String ltrim(String key, int start, int end) {
            return ltrim(SafeEncoder.encode(key), start, end);
        }
    }
}
