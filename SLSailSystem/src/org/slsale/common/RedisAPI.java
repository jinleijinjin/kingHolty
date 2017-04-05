package org.slsale.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAPI {
	
		public JedisPool jedisPool;

		public JedisPool getJedisPool() {
			return jedisPool;
		}

		public void setJedisPool(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
		}
		
		public boolean set(String key,String Value){
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				jedis.set(key, Value);
				return true;
			} catch (Exception e) {
				e.printStackTrace();				
			}finally {
				returnResource(jedisPool, jedis);
			}	
			return false;	
		}
		
		public boolean exist(String key){
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				return jedis.exists(key);
			} catch (Exception e) {
				e.printStackTrace();				
			}finally {
				returnResource(jedisPool, jedis);
			}	
			return false;	
		}
		
		public String get(String key){
			String value=null;
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				value = jedis.get(key);
			} catch (Exception e) {
				e.printStackTrace();				
			}finally {
				returnResource(jedisPool, jedis);
			}	
			return value;
		}
		//释放资源 
		public static void returnResource(JedisPool jedisPool, Jedis jedis){
			if (null!=jedis) {
				jedisPool.returnResource(jedis);
			}			
		}
		
}
