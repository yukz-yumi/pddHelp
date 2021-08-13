package com.yukz.daodaoping.common.amqp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 启动时初始化系统需要的消息队列
 * 
 * @author micezhao
 *
 */
@Configuration
public class AmqpConfig {

	@Value("${ttl.order}")
	private int order_ttl;

	@Value("${ttl.task}")
	private int task_ttl;

	@Bean
	public DirectExchange directExchange() {
		DirectExchange directExchange = new DirectExchange(MqConstants.DIRECT_EXCHANGE);
		return directExchange;
	}

	@Bean
	public Queue directOrderQueue() {
		Queue queue = new Queue(MqConstants.DIRECT_ORDER_QUEUE);
		return queue;
	}

	@Bean
	public Queue directTaskQueue() {
		Queue queue = new Queue(MqConstants.DIRECT_TASK_QUEUE);
		return queue;
	}

	@Bean
	public DirectExchange expireExchange() {
		DirectExchange directExchange = new DirectExchange(MqConstants.EXPIRE_EXECUTION_EXCHANGE, true, false);
		return directExchange;
	}

	@Bean
	public Queue expireTaskQueue() {
		Map<String, Object> argsMap = new HashMap<String, Object>();
		argsMap.put("x-dead-letter-exchange", MqConstants.EXPIRE_EXECUTION_EXCHANGE);
		argsMap.put("x-dead-letter-routing-key", MqConstants.TASK_TTL_KEY);
		argsMap.put("x-message-ttl", task_ttl);
		Queue queue = new Queue(MqConstants.TASK_EXPIRE_QUEUE, true, false, false, argsMap);
		return queue;
	}

	@Bean
	public Queue expireOrderQueue() {
		Map<String, Object> argsMap = new HashMap<String, Object>();
		argsMap.put("x-dead-letter-exchange", MqConstants.EXPIRE_EXECUTION_EXCHANGE);
		argsMap.put("x-dead-letter-routing-key", MqConstants.ORDER_TTL_KEY);
		argsMap.put("x-message-ttl", order_ttl);
		Queue queue = new Queue(MqConstants.ORDER_EXPIRE_QUEUE, true, false, false, argsMap);
		return queue;
	}

	@Bean
	public Queue ttlOrderQueue() {
		Queue queue = new Queue(MqConstants.ORDER_TTL_QUEUE);
		return queue;
	}

	@Bean
	public Queue ttlTaskQueue() {
		Queue queue = new Queue(MqConstants.TASK_TTL_QUEUE);
		return queue;
	}

	@Bean
	public Binding bindingTTLOrder() {
		Binding binding = BindingBuilder.bind(ttlOrderQueue()).to(expireExchange()).with(MqConstants.ORDER_TTL_KEY);
		return binding;
	}

	@Bean
	public Binding bindingTTLTask() {
		Binding binding = BindingBuilder.bind(ttlTaskQueue()).to(expireExchange()).with(MqConstants.TASK_TTL_KEY);
		return binding;
	}

	@Bean
	public Binding bindingOrder() {
		Binding binding = BindingBuilder.bind(directOrderQueue()).to(directExchange())
				.with(MqConstants.ORDER_ROUTER_KEY);
		return binding;
	}

	@Bean
	public Binding bindingTask() {
		Binding binding = BindingBuilder.bind(directTaskQueue()).to(directExchange())
				.with(MqConstants.TASK_ROUTER_KEY);
		return binding;
	}

	@Bean
	public Binding bindingTaskExpire() {
		Binding binding = BindingBuilder.bind(expireTaskQueue()).to(expireExchange())
				.with(MqConstants.TASK_EXPIRE_ROUTER_KEY);
		return binding;
	}

	@Bean
	public Binding bindingOrderExpire() {
		Binding binding = BindingBuilder.bind(expireOrderQueue()).to(expireExchange())
				.with(MqConstants.ORDER_EXPIRE_ROUTER_KEY);
		return binding;
	}

}
