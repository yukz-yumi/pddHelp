package com.yukz.daodaoping.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RedissonConfig {
	
	private static Logger logger= LoggerFactory.getLogger(RedissonConfig.class);
	
	  @Value("${spring.redis.host}")
	  private String redisAddress;
	  
	  @Value("${spring.redis.port}")
	  private String redisPort;
	 
	  @Value("${spring.redis.password}")
	  private String password;
	  

	private Config config(){
	    Config config = new Config();
	    SingleServerConfig singleSerververConfig = config.useSingleServer();
	    singleSerververConfig.setAddress("redis://"+redisAddress+":"+redisPort);
	    singleSerververConfig.setPassword(password);
//	    singleSerververConfig.setDatabase(database);
	    singleSerververConfig.setConnectionPoolSize(64);
	    //如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，
	    // 那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
	    singleSerververConfig.setIdleConnectionTimeout(10000);
	    //同任何节点建立连接时的等待超时。时间单位是毫秒。
	    singleSerververConfig.setConnectTimeout(30000);
	    //等待节点回复命令的时间。该时间从命令发送成功时开始计时。
	    singleSerververConfig.setTimeout(3000);
	    singleSerververConfig.setPingTimeout(30000);
	    //当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
	    //
	    config.setCodec(new org.redisson.client.codec.StringCodec());
	  return config;
	}

	@Bean
	public  RedissonClient redissonClient(){
	  logger.debug("初始化RedissonClient....");
//	  RedissonClient redissonClient = RedisUtil.getInstance().getRedisson(config());
	  RedissonClient redissonClient = Redisson.create(config());
	  return redissonClient;
	}
}
