package com.interview.controller;

import com.interview.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Challenge 1: Request Scope Testing
 * 
 * This controller exposes endpoints to test the OrderProcessingService
 * which should be request-scoped to ensure each HTTP request gets
 * fresh order numbering starting from 1.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderProcessingService orderProcessingService;
    
    @PostMapping("/process")
    public String processOrder(@RequestParam String customerName) {
        return orderProcessingService.processOrder(customerName);
    }
    
    @PostMapping("/batch")
    public String processBatchOrders(@RequestParam String[] customers) {
        StringBuilder result = new StringBuilder();
        for (String customer : customers) {
            if (result.length() > 0) {
                result.append("; ");
            }
            result.append(orderProcessingService.processOrder(customer));
        }
        return result.toString();
    }
    
    @GetMapping("/last-number")
    public int getLastOrderNumber() {
        return orderProcessingService.getLastOrderNumber();
    }
}
