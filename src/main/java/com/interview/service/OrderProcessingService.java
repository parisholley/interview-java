package com.interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * Challenge 1: Request Scope Issue
 * 
 * This service processes orders and assigns sequential order numbers within each request.
 * It should start numbering from 1 for each new request, but due to incorrect scoping,
 * the counter persists across requests.
 * 
 * ISSUE: Service has wrong scope, causing shared state between requests
 * SOLUTION: Change scope to "request" so each HTTP request gets a fresh instance
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
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
