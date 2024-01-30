/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo;

import com.blazartech.rabbitjmsdemo.sender.DemoMessageSender;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 */
@Component
@Slf4j
public class DemoCommandLineRunner implements CommandLineRunner {

    @Autowired
    private DemoMessageSender sender;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("starting to send messages");
        
        List<DemoMessage> messages = List.of(
                new DemoMessage(1, "Scott", 25),
                new DemoMessage(2, "Eddie", 65),
                new DemoMessage(3, "Robert", 79),
                new DemoMessage(4, "Jimmy", 81),
                new DemoMessage(5, "Paul", 82)
        );
        
        messages.stream()
                .peek(m -> log.info("sending {}", m))
                .forEach(m -> sender.sendMessage(m));
    }
    
}
