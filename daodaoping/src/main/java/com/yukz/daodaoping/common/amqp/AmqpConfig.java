package com.yukz.daodaoping.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 启动时初始化系统需要的消息队列
 * @author micezhao
 *
 */
@Configuration
public class AmqpConfig {
	
	
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
		Queue queue = new Queue(MqConstants.DIRECT_ORDER_QUEUE);
		return queue;
	}
	
	@Bean
	public DirectExchange expireExchange() {
		DirectExchange directExchange = new DirectExchange(MqConstants.EXPIRE_EXECUTION_EXCHANGE);
		return directExchange;
	}
	
	@Bean
	public Queue expireTaskQueue() {
		Queue queue = new Queue(MqConstants.TASK_EXPIRE_QUEUE);
		return queue;
	}
	
	@Bean
	public Queue expireOrderQueue() {
		Queue queue = new Queue(MqConstants.ORDER_EXPIRE_QUEUE);
		return queue;
	}
	
	@Bean
    public Binding bindingOrder() {
        Binding binding = BindingBuilder.bind(directOrderQueue()).to(directExchange()).with(MqConstants.ORDER_ROUTER_KEY);
        return binding;
    }
	
	@Bean
    public Binding bindingTask() {
        Binding binding = BindingBuilder.bind(directTaskQueue()).to(directExchange()).with(MqConstants.TASK_ROUTER_KEY);
        return binding;
    }
	
	@Bean
    public Binding bindingTaskExpire() {
        Binding binding = BindingBuilder.bind(expireTaskQueue()).to(expireExchange()).with(MqConstants.TASK_EXPIRE_ROUTER_KEY);
        return binding;
    }
	
	@Bean
    public Binding bindingOrderExpire() {
        Binding binding = BindingBuilder.bind(expireOrderQueue()).to(expireExchange()).with(MqConstants.ORDER_EXPIRE_ROUTER_KEY);
        return binding;
    }
	
	
}
