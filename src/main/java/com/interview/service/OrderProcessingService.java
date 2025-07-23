package com.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that processes orders and assigns sequential order numbers.
 * Order numbering should start from 1 for each new request.
 */
@Service
public class OrderProcessingService {
    
    @Autowired
    private CounterService counterService;
    
    public String processOrder(String customerName) {
        int orderNumber = counterService.getNextValue();
        return String.format("Order #%d for customer: %s", orderNumber, customerName);
    }
    
    public int getLastOrderNumber() {
        return counterService.getCurrentValue();
    }
}
