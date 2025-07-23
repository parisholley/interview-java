package com.interview.challenge1;

import com.interview.service.OrderProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Challenge 1: Spring Bean Scope Problem Test
 * 
 * This test demonstrates the scope issue with CounterService.
 * The test expects each order to get sequential numbers starting from 1,
 * but due to the singleton scope, the counter persists between test methods.
 * 
 * ISSUE: CounterService is singleton scoped, causing state to be shared
 * SOLUTION: Change @Scope("singleton") to @Scope("prototype") in CounterService
 */
@SpringBootTest
class OrderProcessingServiceTest {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @Test
    void testFirstOrderShouldGetNumber1() {
        // This test passes on first run
        String result = orderProcessingService.processOrder("John Doe");
        assertEquals("Order #1 for customer: John Doe", result);
        assertEquals(1, orderProcessingService.getLastOrderNumber());
    }

    @Test
    void testSecondOrderShouldAlsoGetNumber1() {
        // This test FAILS because counter continues from previous test
        // Expected: Order #1, but actual might be Order #2 or higher
        String result = orderProcessingService.processOrder("Jane Smith");
        assertEquals("Order #1 for customer: Jane Smith", result);
        assertEquals(1, orderProcessingService.getLastOrderNumber());
    }

    @Test
    void testMultipleOrdersInSameTest() {
        // This works within the same test method
        String order1 = orderProcessingService.processOrder("Alice");
        String order2 = orderProcessingService.processOrder("Bob");
        
        // But the starting number depends on which tests ran before this one
        assertEquals("Order #1 for customer: Alice", order1);
        assertEquals("Order #2 for customer: Bob", order2);
    }
}
