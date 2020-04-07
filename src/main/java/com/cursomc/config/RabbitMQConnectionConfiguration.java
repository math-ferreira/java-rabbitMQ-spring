package com.cursomc.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cursomc.RabbitMessageListener;

@Configuration
public class RabbitMQConnectionConfiguration {

	private static final String MY_QUEUE = "MyQueue";

	@Bean
	Queue myQueue() {
		return new Queue(MY_QUEUE, true);
	}

	@Bean
	Exchange myExchange() {
		return ExchangeBuilder
				.topicExchange("MyTopicExchange")
				.durable(true)
				.build();
	}

	@Bean
	Binding binding() {
		//return new Binding(MY_QUEUE, Binding.DestinationType.QUEUE, "MyTopicExchange", "topic", null);
		return BindingBuilder
				.bind(myQueue())
				.to(myExchange())
				.with("topic")
				.noargs();
		
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername("guest");
		cachingConnectionFactory.setPassword("guest");
		return cachingConnectionFactory;
	}

	@Bean
	MessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setQueues(myQueue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMessageListener());
		return simpleMessageListenerContainer;
	}
}
