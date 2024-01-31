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
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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
    private final String queue2Name = "jms-demo-consumer-2";
    private final String queue3Name = "jms-demo-consumer-3";
    
    // define the infrastructure as code, though we won't use these.
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName, true, false);
    }
    
    @Bean
    public Queue jmsConsumerQueue1() {
        Queue q = new Queue(queueName);
        return q;
    }
    
    @Bean
    public Queue jmsConsumerQueue2() {
        Queue q = new Queue(queue2Name);
        return q;
    }
    
    @Bean
    public Queue jmsConsumerQueue3() {
        Queue q = new Queue(queue3Name);
        return q;
    }
    
    @Bean
    public Binding jmsConsumerQueue1Binding(TopicExchange topicExchange, Queue jmsConsumerQueue1) {
        return BindingBuilder.bind(jmsConsumerQueue1).to(topicExchange).with(routingKey);
    }
    
    @Bean
    public Binding jmsConsumerQueue2Binding(TopicExchange topicExchange, Queue jmsConsumerQueue2) {
        return BindingBuilder.bind(jmsConsumerQueue2).to(topicExchange).with(routingKey);
    }
    
    @Bean
    public Binding jmsConsumerQueue3Binding(TopicExchange topicExchange, Queue jmsConsumerQueue3) {
        return BindingBuilder.bind(jmsConsumerQueue3).to(topicExchange).with(routingKey);
    }
    
    // now onto the JMS stuff which we will use
    
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
        RMQDestination jmsDestination = new RMQDestination();
        jmsDestination.setAmqp(true);
        jmsDestination.setAmqpQueueName(queueName);
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
    public SimpleMessageListenerContainer container(ConnectionFactory jmsConnectionFactory, Destination consumerQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(jmsConnectionFactory);
        container.setSessionAcknowledgeMode(Session.SESSION_TRANSACTED);
        container.setDestination(consumerQueue);
        container.setMessageListener(demoMessageListener);
        container.setSessionTransacted(true);
        container.setConcurrency("10");
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
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
