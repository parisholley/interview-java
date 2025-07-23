package com.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Service that processes orders and assigns order numbers using CounterService
 * FIXED: Using ApplicationContext to get fresh prototype instances
 */
@Service
public class OrderProcessingService {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    private CounterService currentCounterService;
    
    public String processOrder(String customerName) {
        // Get a fresh prototype instance for each processing session
        if (currentCounterService == null) {
            currentCounterService = applicationContext.getBean(CounterService.class);
        }
        int orderNumber = currentCounterService.getNextValue();
        return String.format("Order #%d for customer: %s", orderNumber, customerName);
    }
    
    public int getLastOrderNumber() {
        if (currentCounterService == null) {
            currentCounterService = applicationContext.getBean(CounterService.class);
        }
        return currentCounterService.getCurrentValue();
    }
    
    // Method to reset the counter service (useful for testing)
    public void resetCounter() {
        currentCounterService = null;
    }
}
