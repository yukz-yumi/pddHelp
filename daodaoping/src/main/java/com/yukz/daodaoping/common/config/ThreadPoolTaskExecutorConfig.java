package com.yukz.daodaoping.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置类
 * @author micezhao
 *
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {
	
	  private static int CORE_POOL_SIZE=50;

	  private static int MAX_POOL_SIZE=10000;
	  
	  @Bean  
	  public ThreadPoolTaskExecutor taskExecutor(){  
		  
	          ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor(); 
	          //线程池维护线程的最少数量  
	          poolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);  
	          //线程池维护线程的最大数量  
	          poolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);  
	          //线程池所使用的缓冲队列 ，线程> 200 就要排队了
	          poolTaskExecutor.setQueueCapacity(200);  
	          //线程池维护线程所允许的空闲时间  线程超过30秒没被使用，就回收
	          poolTaskExecutor.setKeepAliveSeconds(30);  
	          poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);  
	          return poolTaskExecutor;  
	  }  
}
