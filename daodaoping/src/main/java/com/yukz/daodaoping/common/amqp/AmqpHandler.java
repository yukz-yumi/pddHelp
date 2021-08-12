package com.yukz.daodaoping.common.amqp;

import java.util.Calendar;
import java.util.Date;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AmqpHandler {
	
	 @Autowired
	 private AmqpTemplate rabbitTemplate;

	 /**
	   * 延迟消息队列消息发送
	   * @param object
	   * @param routeKey
	   * @param excuteDate
	   */
	 
	  public void sendDelayMessage(Object object,String routeKey,Date excuteDate){
	    MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
	      @Override
	      public Message postProcessMessage(Message message) throws AmqpException {
	        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
	        Integer time =(int) (excuteDate.getTime() - Calendar.getInstance().getTime().getTime());
	        message.getMessageProperties().setDelay(time);   // 毫秒为单位，指定此消息的延时时长
	        return message;
	      }
	    };
	    rabbitTemplate.convertAndSend(MqConstants.EXPIRE_EXECUTION_EXCHANGE,routeKey,object,messagePostProcessor);
	  }
	  
	  public void sendToDirectQueue(String routeKey,Object object) {
		  rabbitTemplate.convertAndSend(MqConstants.DIRECT_EXCHANGE,routeKey, object);
	  }
	  
}
