package com.interview.service;

import org.springframework.stereotype.Service;

/**
 * Service that provides a counter that increments for each request.
 */
@Service
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
