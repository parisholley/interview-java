package com.interview.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Challenge 1: Spring Bean Scope Problem
 * 
 * This service is intended to provide a counter that increments for each request.
 * However, there's a scope configuration issue causing unexpected behavior.
 * 
 * Expected: Each test method should get independent counter instances
 * Actual: Counter values are being shared/reused unexpectedly
 */
@Service
@Scope("prototype") // FIXED: Changed from singleton to prototype for proper isolation
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
