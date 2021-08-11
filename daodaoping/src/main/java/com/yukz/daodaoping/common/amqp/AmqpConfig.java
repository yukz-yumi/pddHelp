package com.yukz.daodaoping.common.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public Binding bindingOrder() {
        Binding binding = BindingBuilder.bind(directOrderQueue()).to(directExchange()).with(MqConstants.ORDER_ROUTER_KEY);
        return binding;
    }


}
