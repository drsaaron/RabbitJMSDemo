/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.listener;

import com.blazartech.rabbitjmsdemo.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import static org.springframework.amqp.support.AmqpHeaders.DELIVERY_TAG;
import static org.springframework.amqp.support.AmqpHeaders.RECEIVED_ROUTING_KEY;
import com.rabbitmq.client.Channel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * mix in a rabbit listener to show the interoperability of JMS publishing (this way)
 * and rabbit consuming.
 * @author aar1069
 */
@Component
@Slf4j
public class RabbitDemoMessageListener {
    
    @RabbitListener(queues = "jms-demo-consumer-3", concurrency = "12", messageConverter = "jsonMessageConverter")
    public void onMessage(DemoMessage demoMessage, Channel channel, @Header(DELIVERY_TAG) long deliveryTag, @Header(RECEIVED_ROUTING_KEY) String topic) {
        log.info("got message {} on topic {}", demoMessage, topic);
    }
}
