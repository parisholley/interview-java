package com.interview.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Counter service that maintains order numbers.
 * Each instance starts counting from 0 and increments with each call.
 * 
 * This service is prototype-scoped, meaning each injection point gets a fresh instance.
 */
@Service
@Scope("prototype")
public class CounterService {
    
    private int count = 0;
    
    public int getNextValue() {
        return ++count;
    }
    
    public int getCurrentValue() {
        return count;
    }
    
    public void reset() {
        count = 0;
    }
}
