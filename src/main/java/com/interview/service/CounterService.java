package com.interview.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Challenge 2: Request-Scoped Counter Service (formerly Challenge 1)
 * 
 * This service provides sequential numbering within each HTTP request.
 * Each request-scoped OrderProcessingService should get its own fresh counter.
 * 
 * With prototype scope: Each @Autowired injection gets a new instance
 * With singleton scope: All injections share the same instance (wrong for this use case)
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
