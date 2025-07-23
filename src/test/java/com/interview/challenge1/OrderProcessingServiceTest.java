package com.interview.challenge1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Challenge 1: Order Processing Test
 * 
 * Tests order processing functionality through HTTP requests.
 * Order numbering should start from 1 for each new request.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderProcessingServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSingleOrderProcessing() {
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
    void testMultipleIndividualOrders() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("customerName", "Bob");
        
        String response = restTemplate.postForObject(
            "http://localhost:" + port + "/api/orders/process", 
            params, 
            String.class
        );
        
        // Expecting fresh order numbering for new request
        assertEquals("Order #1 for customer: Bob", response);
    }

    @Test
    void testBatchOrderProcessing() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("customers", "Charlie");
        params.add("customers", "Diana");
        params.add("customers", "Eve");
        
        String response = restTemplate.postForObject(
            "http://localhost:" + port + "/api/orders/batch", 
            params, 
            String.class
        );
        
        // Expecting sequential numbering within the same batch request
        assertEquals("Order #1 for customer: Charlie; Order #2 for customer: Diana; Order #3 for customer: Eve", response);
    }
}
