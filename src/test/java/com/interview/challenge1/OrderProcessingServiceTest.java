package com.interview.challenge1;

import com.interview.service.OrderProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Challenge 1: Order Processing Test
 * 
 * This test is failing. The order numbers are not behaving as expected.
 */
@SpringBootTest
class OrderProcessingServiceTest {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @Test
    void testFirstOrderShouldGetNumber1() {
        String result = orderProcessingService.processOrder("John Doe");
        assertEquals("Order #1 for customer: John Doe", result);
        assertEquals(1, orderProcessingService.getLastOrderNumber());
    }

    @Test
    void testSecondOrderShouldAlsoGetNumber1() {
        String result = orderProcessingService.processOrder("Jane Smith");
        assertEquals("Order #1 for customer: Jane Smith", result);
        assertEquals(1, orderProcessingService.getLastOrderNumber());
    }

    @Test
    void testMultipleOrdersInSameTest() {
        String order1 = orderProcessingService.processOrder("Alice");
        String order2 = orderProcessingService.processOrder("Bob");
        
        assertEquals("Order #1 for customer: Alice", order1);
        assertEquals("Order #2 for customer: Bob", order2);
    }
}
