package com.terri.redis.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;



public class RedisClient {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    
    public RedisClient() 
    { 
        initialPool(); 
        initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
    } 
 
    /**
     * 初始化非切片池
     */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
//        config.setMaxActive(20); 
        config.setMaxIdle(5); 
 //       config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
 //       config.setMaxActive(20); 
        config.setMaxIdle(5); 
  //      config.setMaxWait(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); 

        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
    } 

    public void show() {     
        KeyOperate(); 
        StringOperate(); 
        ListOperate(); 
        SetOperate();
        SortedSetOperate();
        HashOperate(); 
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    } 

      private void KeyOperate() {
    	  // 获取数据并输出
    	     Set<String> keys = jedis.keys("*");
    	     for(String key:keys) {
    	       System.out.println("List of stored keys:: "+key);
    	     }
      }

      private void StringOperate() {
    	    jedis.set("w3ckey", "Redis tutorial");
    	     // 获取存储的数据并输出
    	     System.out.println("Stored string in redis:: "+ jedis.get("w3ckey"));
      }

      private void ListOperate() {
    	  
    	  jedis.lpush("tutorial-list", "Redis");
          jedis.lpush("tutorial-list", "Mongodb");
          jedis.lpush("tutorial-list", "Mysql");
         // 获取存储的数据并输出
         List<String> list = jedis.lrange("tutorial-list", 0 ,5);
         for(int i=0; i<list.size(); i++) {
           System.out.println("Stored string in redis:: "+list.get(i));
         }
         
      }

      private void SetOperate() {
        // 。。。
      }

      private void SortedSetOperate() {
         //。。。
      }
    
      private void HashOperate() {
         //。。。
      }
}