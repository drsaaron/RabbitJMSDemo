/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.rabbitjmsdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 *
 * @author scott
 */
@Component
@Slf4j
public class LoggingErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable e) {
        log.error("error handling message: " + e.getMessage(), e);
    }
    
}
