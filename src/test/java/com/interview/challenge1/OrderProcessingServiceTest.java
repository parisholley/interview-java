package com.interview.challenge1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Challenge 1: Request Scope Problem Test
 * 
 * This test demonstrates the request scope issue with OrderProcessingService.
 * Each HTTP request should get a fresh OrderProcessingService instance with its own
 * counter starting from 1, but due to incorrect scoping, the counter may persist
 * across requests.
 * 
 * ISSUE: OrderProcessingService scope is wrong, causing shared state between requests
 * SOLUTION: Use @Scope(WebApplicationContext.SCOPE_REQUEST) on OrderProcessingService
 *           and @Scope("prototype") on CounterService
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderProcessingServiceTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testFirstRequestShouldStartOrderNumberingAt1() {
        String baseUrl = "http://localhost:" + port + "/api/orders";
        
        // First request should start numbering at 1
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("customerName", "Alice");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        
        String response = restTemplate.postForObject(baseUrl + "/process", request, String.class);
        assertEquals("Order #1 for customer: Alice", response);
    }

    @Test
    void testSecondRequestShouldAlsoStartAt1() {
        String baseUrl = "http://localhost:" + port + "/api/orders";
        
        // Second request should also start numbering at 1 (fresh request scope)
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("customerName", "Bob");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        
        String response = restTemplate.postForObject(baseUrl + "/process", request, String.class);
        assertEquals("Order #1 for customer: Bob", response);
    }

    @Test
    void testMultipleOrdersInSameRequestShouldIncrement() {
        String baseUrl = "http://localhost:" + port + "/api/orders";
        
        // Within the same request, orders should increment: 1, 2, 3...
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("customers", "Charlie");
        params.add("customers", "Diana");
        params.add("customers", "Eve");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        
        String response = restTemplate.postForObject(baseUrl + "/batch", request, String.class);
        
        // Should increment within the same request
        assertTrue(response.contains("Order #1 for customer: Charlie"));
        assertTrue(response.contains("Order #2 for customer: Diana"));
        assertTrue(response.contains("Order #3 for customer: Eve"));
    }
}
