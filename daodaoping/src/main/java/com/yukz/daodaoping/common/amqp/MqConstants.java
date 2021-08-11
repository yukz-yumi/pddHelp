package com.yukz.daodaoping.common.amqp;

/**
 * 消息中间件常量类
 * @author micezhao
 *
 */
public class MqConstants {
	
	public static final String  DELAY_TASK_EXECUTION_QUEUE = "delay_task_execution_queue";
	
	public static final String DELAY_DISPOSE_ROUTER_KEY = "delay_dispose_router";
	
	public static final String DELAY_ORDER_EXPIRE_ROUTER_KEY = "delay_order_expire_key";
	
//	public static final String  DELAY_ORDER_EXPIRE_QUEUE = "delay_order_expire_queue";
	
	public static final String DIRECT_ORDER_QUEUE = "direct_order_queue";
	
	public static final String ORDER_ROUTER_KEY = "order_router_key";
	
	public static final String DIRECT_EXCHANGE = "direct_exchange";
}
