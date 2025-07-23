package com.interview.challenge1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Challenge 1: Order Processing Request Scope Test
 * 
 * This test demonstrates that order numbering should reset to 1 for each new HTTP request,
 * but due to incorrect bean scoping, the counter persists across requests.
 * 
 * The tests use actual HTTP requests to simulate real client behavior.
 * Each request should start order numbering from 1, but currently they continue incrementing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderProcessingServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFirstRequestShouldStartOrderNumberingAt1() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("customerName", "Alice");
        
        String response = restTemplate.postForObject(
            "http://localhost:" + port + "/api/orders/process", 
            params, 
            String.class
        );
        
        assertEquals("Order #1 for customer: Alice", response);
    }

    @Test
    void testSecondRequestShouldAlsoStartAt1() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("customerName", "Bob");
        
        String response = restTemplate.postForObject(
            "http://localhost:" + port + "/api/orders/process", 
            params, 
            String.class
        );
        
        // This should be "Order #1 for customer: Bob" but will likely be a higher number
        // due to the scoping issue
        assertEquals("Order #1 for customer: Bob", response);
    }

    @Test
    void testMultipleOrdersInSameRequestShouldIncrement() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("customers", "Charlie");
        params.add("customers", "Diana");
        params.add("customers", "Eve");
        
        String response = restTemplate.postForObject(
            "http://localhost:" + port + "/api/orders/batch", 
            params, 
            String.class
        );
        
        // Within the same request, orders should increment: 1, 2, 3
        assertEquals("Order #1 for customer: Charlie; Order #2 for customer: Diana; Order #3 for customer: Eve", response);
    }
}
