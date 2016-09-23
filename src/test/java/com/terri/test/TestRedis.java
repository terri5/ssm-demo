package com.terri.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.terri.sys.util.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import sun.security.tools.jarsigner.TimestampedSigner;

public class TestRedis {
	private Jedis jedis;

	@Before
	public void setup() {
		// 连接redis服务器，192.168.0.100:6379
		// jedis = new Jedis("192.168.0.100", 6379);
		// jedis = new Jedis("localhost", 6379);
		// 权限认证
		// jedis.auth("admin");
		// jedis = new Jedis("gz.redis.cache.chinacloudapi.cn", 6379);
		// jedis.auth("pP/BXXELrGVF3kGWvMWFYbVdfOTGMKUdOFSaV2Tf1k8=");

		// jedis = new Jedis("redis-statis.chinacloudapp.cn", 6379);
		// jedis.auth("M2VjMGIxZGM0M2NjOGZjOGM2ZjkyNTAw");

	}

	@Test
	public void RemoveAfterkeys() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHH");
		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);
		// gc.add(GregorianCalendar.HOUR,1);
		for (int i = 1; i < 60; i++) {
			System.out.println("del " + sf.format(gc.getTime()).substring(0, 8));
			String key3 = "wifilog:b:v1:" + sf.format(gc.getTime()).substring(0, 8);
			String key4 = "wifilog:b:v2:" + sf.format(gc.getTime()).substring(0, 8);
			String key5 = "wifilog:t:v1:" + sf.format(gc.getTime()).substring(0, 8);
			String key6 = "wifilog:t:v2:" + sf.format(gc.getTime()).substring(0, 8);
			for (int j = 0; j < 24; j++) {
				gc.add(GregorianCalendar.HOUR, 1);
				String key1 = "wifilog:b:wlan:user:" + sf.format(gc.getTime());
				String key2 = "wifilog:t:wlan:user:" + sf.format(gc.getTime());
				jedis.del(key1, key2);
				System.out.println("del " + key1 + " " + key2);
			}
			jedis.del(key3, key4, key5, key6);
		}

	}

	@Test
	public void RemoveBeforekeys() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);

		gc.add(GregorianCalendar.DAY_OF_YEAR, -7);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			Pipeline p = jedis.pipelined();
			String key_t = "wifilog:t:v1:" + sf.format(gc.getTime());
			// gc.add(GregorianCalendar.DAY_OF_MONTH,-1);
			// System.out.println("del "+key_t);
			// jedis.del(key_t);
			p.del(key_t);
			String key_t2 = "wifilog:t:v2:" + sf.format(gc.getTime());
			// System.out.println("del "+key_t2);
			p.del(key_t2);

			String key_b = "wifilog:b:v1:" + sf.format(gc.getTime());
			// System.out.println("del "+key_b);
			p.del(key_b);
			String key_b2 = "wifilog:b:v2:" + sf.format(gc.getTime());
			// System.out.println("del "+key_b2);

			p.del(key_b2);

			String kpvut = "wifilog:t:wlan:pv:url:" + sf.format(gc.getTime());
			// System.out.println("del "+kpvut);
			p.del(kpvut);

			String kpvub = "wifilog:b:wlan:pv:url:" + sf.format(gc.getTime());
			// System.out.println("del "+kpvub);
			p.del(kpvub);

			for (int j = 0; j < 24; j++) {
				gc.add(GregorianCalendar.HOUR, -1);
				String keyh = "wifilog:b:wlan:user:" + sf.format(gc.getTime()) + (j < 10 ? "0" + j : j + "");
				// System.out.println("del "+keyh);
				p.del(keyh);

				String keyht = "wifilog:t:wlan:user:" + sf.format(gc.getTime()) + (j < 10 ? "0" + j : j + "");
				// System.out.println("del "+keyht);
				p.del(keyht);

				String keyhbl = "wifilog:b:lan:user:" + sf.format(gc.getTime()) + (j < 10 ? "0" + j : j + "");
				// System.out.println("del "+keyhbl);
				p.del(keyhbl);

				String keyhtl = "wifilog:t:lan:user:" + sf.format(gc.getTime()) + (j < 10 ? "0" + j : j + "");
				// System.out.println("del "+keyhtl);
				p.del(keyhtl);

			}

			List<Object> results = p.syncAndReturnAll();
			long end = System.currentTimeMillis();
			System.out.println(gc.getTime() + " Pipelined del: " + ((end - start) / 1000.0) + " seconds");

			// jedis.disconnect();
		}

	}

	@Test
	public void removeUrluvSet() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_YEAR, -3);
		for (int i = 0; i < 30; i++) {
			  Set<String> keys=new HashSet<String>();
			  System.err.println("当前时间："+gc.getTime().toString());
			  gc.add(GregorianCalendar.DAY_OF_YEAR, -1);
     	String key1 = "wifilog:t:wlan:pv:url:" + sf.format(gc.getTime());
				if (jedis.exists(key1)) {
					Set<String> s = jedis.zrevrange(key1, 0, 50);
					String keyUvUrlPattern = "wifilog:t:wlan:uv:url:" + sf.format(gc.getTime()) + ":*";
					Set<String> keyurl = jedis.keys(keyUvUrlPattern);
					for (String key : keyurl) {
						if (!s.contains(key.substring(key.lastIndexOf(":") + 1))) {
							System.out.println("del " + key);
							keys.add(key);
					//		jedis.del(key);
						}
		
					}
				}
		
				String key2 = "wifilog:b:wlan:pv:url:" + sf.format(gc.getTime());
				if (jedis.exists(key2)) {
					Set<String> s2 = jedis.zrevrange(key1, 0, 50);
					String keyUvUrlPattern2 = "wifilog:b:wlan:uv:url:" + sf.format(gc.getTime()) + ":*";
					Set<String> keyurl2 = jedis.keys(keyUvUrlPattern2);
					for (String key : keyurl2) {
							if (!s2.contains(key.substring(key.lastIndexOf(":") + 1))) {
								System.out.println("del " + key);
								keys.add(key);
							//	jedis.del(key);
							}
					}
				}
				Pipeline p=jedis.pipelined();
				if(keys.size()>0){
					for (String key : keys) {
						p.del(key);			
					}
					p.sync();
				}	
			}
}

	@Test
	public void getUrlPv() {

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_YEAR, -30);
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		gc.add(gc.HOUR, -15);
		Character filedS = ',';
		for (int i = 0; i < 31; i++) {
			// System.out.println("火车:"+sf.format(gc.getTime()));
			sb1.append("火车:" + sf.format(gc.getTime())).append(System.lineSeparator());
			String key1 = "wifilog:t:wlan:pv:url:" + sf.format(gc.getTime());
			Set<Tuple> s = jedis.zrevrangeWithScores(key1, 0, 50);
			for (Tuple tmp : s) {
				// System.out.println(tmp.getElement()+filedS+Math.round(tmp.getScore()));
				sb1.append(tmp.getElement() + filedS + Math.round(tmp.getScore())).append(System.lineSeparator());
			}
			// System.out.println("大巴"+sf.format(gc.getTime()));
			sb2.append("大巴:" + sf.format(gc.getTime())).append(System.lineSeparator());
			Set<Tuple> s2 = jedis.zrevrangeWithScores(key1, 0, 50);
			for (Tuple tmp : s2) {
				// System.out.println(tmp.getElement()+filedS+Math.round(tmp.getScore()));
				sb2.append(tmp.getElement() + filedS + Math.round(tmp.getScore())).append(System.lineSeparator());
			}
			gc.add(gc.DAY_OF_YEAR, 1);
		}
		System.out.println(sb1.toString() + sb2.toString());

	}

	@Test
	public void getRange() {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setGroupingUsed(false);

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHH");

		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);
		gc.add(gc.HOUR, -13);
		// gc.add(gc.DAY_OF_YEAR,-1);
		for (int k = 0; k < 7; k++) {

			double inScore = 0.0;
			double outScore = 0.0;
			Set<String> inS = new HashSet<String>();
			Set<String> outS = new HashSet<String>();
			Set<String> s = new HashSet<String>();

			for (int i = 0; i < 24; i++) {
				gc.add(gc.HOUR, -1);
				// System.out.println(sf.format(gc.getTime()));
				String key_t = "wifilog:t:wlan:user:" + sf.format(gc.getTime()).substring(0, 10);
				Set<Tuple> t1 = jedis.zrangeWithScores(key_t, 0, -1);
				for (Tuple tmp : t1) {
					outScore += tmp.getScore();
					outS.add(tmp.getElement());
					s.add(tmp.getElement());
				}
				key_t = "wifilog:t:lan:user:" + sf.format(gc.getTime()).substring(0, 10);
				Set<Tuple> t2 = jedis.zrangeWithScores(key_t, 0, -1);
				for (Tuple tmp : t2) {
					inScore += tmp.getScore();
					inS.add(tmp.getElement());
					s.add(tmp.getElement());
				}

			}
			System.out.println(sf.format(gc.getTime()).substring(0, 8) + "　外网用户数" + outS.size() + " pv数："
					+ nf.format(inScore) + " 内网用户数：" + inS.size() + " pv数" + nf.format(outScore) + "总uv数：" + s.size());
		}

	}

	@Test
	public void statistics() {
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHH");
		Set<String> s = new HashSet<String>();

		Date d = Calendar.getInstance().getTime();
		gc.setTime(d);
		gc.add(gc.DAY_OF_YEAR, -3);
		gc.add(gc.HOUR, -16);
		Double sc = 0.0;
		for (int i = 0; i < 24; i++) {
			String key_t = "wifilog:b:wlan:user:" + sf.format(gc.getTime()).substring(0, 10);
			System.out.println(key_t);
			Set<String> t = jedis.zrange(key_t, 0, -1);

			System.out.println("size=" + t.size());
			s.addAll(t);
			gc.add(gc.HOUR, 1);
			// for(String tmp:t){
			// sc+=jedis.zscore(key_t,tmp);
			// }

		}
		System.out.println("size:" + s.size() + " score:" + sc);

	}

	@Test
	public void RemoveYearKeys() {
		String year = "2015";
		Set<String> keys = jedis.keys("wifilog:b:wlan:user:" + year + ".*");
		for (String key : keys) {
			System.out.println("del " + key);
		}
	}

	/**
	 * redis存储字符串
	 */
	@Test
	public void testString() {
		// -----添加数据----------
		jedis.set("name", "xinxin");// 向key-->name中放入了value-->xinxin
		System.out.println(jedis.get("name"));// 执行结果：xinxin

		jedis.append("name", " is my lover"); // 拼接
		System.out.println(jedis.get("name"));

		jedis.del("name"); // 删除某个键
		System.out.println(jedis.get("name"));
		// 设置多个键值对
		jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
		jedis.incr("age"); // 进行加1操作
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

	}

	/**
	 * redis操作Map
	 */
	@Test
	public void testMap() {
		// -----添加数据----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		// 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// 删除map中的某个键值
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * jedis操作List
	 */
	@Test
	public void testList() {
		// 开始前，先移除所有的内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// 先向key java framework中存放三条数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// 再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}

	/**
	 * jedis操作Set
	 */
	@Test
	public void testSet() {
		// 添加
		jedis.sadd("user", "liuling");
		jedis.sadd("user", "xinxin");
		jedis.sadd("user", "ling");
		jedis.sadd("user", "zhangxinxin");
		jedis.sadd("user", "who");
		// 移除noname
		jedis.srem("user", "who");
		System.out.println(jedis.smembers("user"));// 获取所有加入的value
		System.out.println(jedis.sismember("user", "who"));// 判断 who
															// 是否是user集合的元素
		System.out.println(jedis.srandmember("user"));
		System.out.println(jedis.scard("user"));// 返回集合的元素个数
	}

	@Test
	public void test() throws InterruptedException {
		// jedis 排序
		// 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
		jedis.del("a");// 先清除数据，再加入数据进行测试
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果
		System.out.println(jedis.lrange("a", 0, -1));
	}

	@Test
	public void testRedisPool() {
		RedisUtil.getJedis().set("newname", "中文测试");
		System.out.println(RedisUtil.getJedis().get("newname"));
	}
}