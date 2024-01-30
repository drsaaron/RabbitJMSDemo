/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.config;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageListener;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.util.ErrorHandler;

/**
 *
 * @author aar1069
 */
@Configuration
@Slf4j
public class RabbitJMSConfiguration {
    
    private final String exchangeName = "jms-demo";
    private final String routingKey = "jms-sender-1";
    private final String queueName = "jms-demo-consumer-1";
    
    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }
    
    @Bean
    public Destination consumerQueue() {
//        RMQDestination jmsDestination = new RMQDestination(queueName, true, false);
        RMQDestination jmsDestination = new RMQDestination();
        jmsDestination.setAmqp(true);
//        jmsDestination.setAmqpExchangeName(exchangeName);
        jmsDestination.setAmqpQueueName(queueName);
//        jmsDestination.setAmqpRoutingKey(routingKey);
        jmsDestination.setQueue(true);
        jmsDestination.setDestinationName(queueName);

        log.info("jmsDestination = {}", jmsDestination);
        return jmsDestination;
    }
    
    @Autowired
    private MessageListener demoMessageListener;
    
    @Autowired
    private ErrorHandler loggingErrorHandler;
    
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory jmsConnectionFactory, Queue consumerQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(jmsConnectionFactory);
        container.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        container.setDestination(consumerQueue);
        container.setMessageListener(demoMessageListener);
        container.setSessionTransacted(true);
        container.setConcurrency("5");
        container.setErrorHandler(loggingErrorHandler);

        return container;
    }
    
    @Bean
    public Destination publishTopic() {
        RMQDestination jmsDestination = new RMQDestination();
        jmsDestination.setAmqp(true);
        jmsDestination.setAmqpExchangeName(exchangeName);
        jmsDestination.setAmqpRoutingKey(routingKey);
        jmsDestination.setQueue(false);
        jmsDestination.setDestinationName(exchangeName);

        log.info("publishQueue = {}", jmsDestination);
        return jmsDestination;
    }
}
