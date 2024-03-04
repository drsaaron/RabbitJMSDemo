/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.sender;

import com.blazartech.rabbitjmsdemo.DemoMessage;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class DemoMessageSenderDestinationAsyncImpl implements DemoMessageSenderAsync {

    @Autowired
    private DemoMessageSender messageSender;
    
    @Override
    public Future<Void> sendMessage(DemoMessage message) {
        log.info("sending message {}", message);
        messageSender.sendMessage(message);
        return CompletableFuture.completedFuture(null);
    }
    
}
