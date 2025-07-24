package com.interview.controller;

import com.interview.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            if (i > 0) result.append("; ");
            result.append(orderProcessingService.processOrder(customers[i]));
        }
        return result.toString();
    }
}
