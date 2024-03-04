/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.sender;

import com.blazartech.rabbitjmsdemo.DemoMessage;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author aar1069
 */
public interface DemoMessageSenderAsync {
    
    @Async
    public Future<Void> sendMessage(DemoMessage message);
}
