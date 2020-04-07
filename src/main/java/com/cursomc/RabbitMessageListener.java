package com.cursomc;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		System.out.println("message = [ " + new String(message.getBody()) + " ]");
		
	}

	
}
