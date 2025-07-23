package com.interview.controller;

import com.interview.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Challenge 2: Request Scope Testing (formerly Challenge 1)
 * 
 * This controller processes orders via HTTP endpoints.
 * The order numbering should reset to 1 for each new HTTP request,
 * but due to incorrect bean scoping, numbers continue incrementing across requests.
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
        for (int i = 0; i < customers.length; i++) {
            if (i > 0) {
                result.append("; ");
            }
            result.append(orderProcessingService.processOrder(customers[i]));
        }
        return result.toString();
    }

    @GetMapping("/last-number")
    public int getLastOrderNumber() {
        return orderProcessingService.getLastOrderNumber();
    }
}
