package com.yukz.daodaoping.common.amqp;

/**
 * 消息中间件常量类
 * @author micezhao
 *
 */
public class MqConstants {
	
	/**
	 * 发单路由键
	 */
	public static final String DELAY_DISPOSE_ROUTER_KEY = "delay_dispose_router";
	
	public static final String EXPIRE_EXECUTION_EXCHANGE = "expire_execution_exchange";
	// 任务过期
	public static final String TASK_EXPIRE_ROUTER_KEY = "task_expire_router_key";
	// 订单过期
	public static final String ORDER_EXPIRE_ROUTER_KEY = "order_expire_key";
	// 接单处理过期时间
	public static final String TASK_TAKE_EXPIRE_ROUTER_KEY = "task_take_expire_key";
	
	public static final String ORDER_TTL_KEY = "order_ttl_key";
	
	public static final String ORDER_TTL_QUEUE = "order_ttl_queue";
	
	public static final String TASK_TTL_KEY = "task_ttl_key";
	
	public static final String TASK_TTL_QUEUE = "task_ttl_queue";
	
	public static final String TASK_EXPIRE_QUEUE = "task_expire_queue";
	
	public static final String ORDER_EXPIRE_QUEUE = "order_expire_queue";
	
	public static final String TASK_TAKE_EXPIRE_QUEUE = "task_take_expire_queue";
	
	public static final String TASK_TAKE_TTL_KEY = "task_take_ttl_key"; 
	
	public static final String TASK_TAKE_TTL_QUEUE = "task_take_ttl_queue";
	
	/**
	 * 订单队列
	 */
	public static final String DIRECT_ORDER_QUEUE = "direct_order_queue";
	
	/**
	 * 订单路邮键
	 */
	public static final String ORDER_ROUTER_KEY = "order_router_key";
	
	/**
	 * 任务队列
	 */
	public static final String  DIRECT_TASK_QUEUE = "direct_task_queue";
	
	/**
	 * 任务路邮键
	 */
	public static final String TASK_ROUTER_KEY = "task_router_key";
	
	/**
	 * 任务路邮键
	 */
	public static final String TASK_TAKE_ROUTER_KEY = "task_take_router_key";
	
	/**
	 * 任务接单队列
	 */
	public static final String DIRECT_TASK_TAKE_QUEUE = "direct_task_take_queue";
	
	/**
	 * 直连交换机
	 */
	public static final String DIRECT_EXCHANGE = "direct_exchange";
}
