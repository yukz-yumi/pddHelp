package com.yukz.daodaoping.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

/**
 * 有序的编码生成器 
 * @author micezhao
 *
 */
@Service
public class SerialNumGenerator {
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	/**
	 * 生成单据号
	 * @param prefixBizCode
	 * @param timeFormatter
	 * @param numLength max:8
	 * @return
	 */
	public String getSerialBizId(String prefixBizCode, String timeFormatter, int numLength) {
	   String serialNumLength="";
	   for(int i=0; i<numLength; i++){
	      serialNumLength+="0";
	   }
	   if(StringUtils.isEmpty(timeFormatter)){
	      timeFormatter="yyyyMMdd"; //yyyyMMdd
	   }
	   //默认序列号位数
	   if(StringUtils.isEmpty(serialNumLength)){//默认
	      serialNumLength="000000";
	   }

	   SimpleDateFormat sdf=new SimpleDateFormat(timeFormatter);
	   Date date=new Date();
	   String formatDate=sdf.format(date);
	   String key= formatDate;  
	   if(StringUtils.isNoneEmpty(prefixBizCode)) {
		    key= prefixBizCode+":"+formatDate;  
	   }
	   Long incr = getIncr(key, getCurrent2TodayEndMillisTime());
	   if(incr==0) {
	      incr = getIncr(key, getCurrent2TodayEndMillisTime());//从000001开始
	   }
	   DecimalFormat df=new DecimalFormat(serialNumLength);//流水号位数
	   return prefixBizCode+formatDate+df.format(incr);
	}

	/**
	 *  根据key获取redis值
	 * @param key
	 * @param liveTime
	 * @return
	 */
	private Long getIncr(String key, long liveTime) {
	   RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
	   Long increment = entityIdCounter.getAndIncrement();

	   if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
	      entityIdCounter.expire(liveTime, TimeUnit.MILLISECONDS);//单位毫秒
	   }
	   return increment;
	}

	/**
	 * 系统日期的毫秒数
	 * @return
	 */
	private Long getCurrent2TodayEndMillisTime() {
	   Calendar todayEnd = Calendar.getInstance();
	   // Calendar.HOUR 12小时制
	   // HOUR_OF_DAY 24小时制
	   todayEnd.set(Calendar.HOUR_OF_DAY, 23);
	   todayEnd.set(Calendar.MINUTE, 59);
	   todayEnd.set(Calendar.SECOND, 59);
	   todayEnd.set(Calendar.MILLISECOND, 999);
	   return todayEnd.getTimeInMillis()-new Date().getTime();
	}
}
