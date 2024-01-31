/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.listener;

import com.blazartech.rabbitjmsdemo.DemoMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class JMSDemoMessageListener implements MessageListener {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void onMessage(Message msg) {
        log.info("received message {}", msg);
        
        try {
            String messageJson;
            switch (msg) {
                case TextMessage textMessage -> messageJson = textMessage.getText();
                case BytesMessage bytesMessage -> {
                    int messageLength = (int) bytesMessage.getBodyLength();
                    byte[] bytes = new byte[messageLength];
                    bytesMessage.readBytes(bytes);
                    messageJson = new String(bytes);
                }
                default -> throw new IllegalStateException("object is neither text nor bytes");
            }
            
            DemoMessage demoMessage = objectMapper.readValue(messageJson, DemoMessage.class);
            log.info("demoMessage = {}", demoMessage);
            
            if (demoMessage.getId() % 4 == 0) {
             //   throw new RuntimeException("intentional fail");
            }
        } catch (JMSException |JsonProcessingException e) {
            log.error("got exception: ", e.getMessage(), e);
        }
    }
    
}
