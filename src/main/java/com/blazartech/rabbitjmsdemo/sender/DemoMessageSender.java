/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.sender;

import com.blazartech.rabbitjmsdemo.DemoMessage;

/**
 *
 * @author aar1069
 */
public interface DemoMessageSender {
    
    public void sendMessage(DemoMessage message);
}
