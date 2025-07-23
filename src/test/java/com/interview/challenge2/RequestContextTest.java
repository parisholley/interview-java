package com.interview.challenge2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Challenge 2: ThreadLocal Memory Leak Test
 * 
 * This test demonstrates the ThreadLocal cleanup issue in RequestContextFilter.
 * When using the same thread (common in test environments), context from previous
 * requests can leak into subsequent requests.
 * 
 * ISSUE: RequestContextFilter doesn't call RequestContextHolder.clear()
 * SOLUTION: Add RequestContextHolder.clear() in the finally block
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class RequestContextTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testThreadLocalCleanup() {
        String baseUrl = "http://localhost:" + port;

        // First request with user context
        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("X-User-ID", "user123");
        headers1.set("X-Session-ID", "session456");
        HttpEntity<?> entity1 = new HttpEntity<>(headers1);

        ResponseEntity<String> response1 = restTemplate.exchange(
                baseUrl + "/api/user/context",
                HttpMethod.GET,
                entity1,
                String.class
        );

        assertEquals(200, response1.getStatusCode().value());
        assertTrue(response1.getBody().contains("user123"));
        assertTrue(response1.getBody().contains("session456"));

        // Second request WITHOUT user context headers
        // This should show "No context" but might show leaked context from first request
        HttpHeaders headers2 = new HttpHeaders();
        HttpEntity<?> entity2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(
                baseUrl + "/api/user/context",
                HttpMethod.GET,
                entity2,
                String.class
        );

        assertEquals(200, response2.getStatusCode().value());
        
        // This assertion might FAIL due to ThreadLocal leakage
        // Expected: "No context"
        // Actual: might contain "user123" and "session456" from previous request
        assertEquals("No context", response2.getBody());
    }

    @Test
    void testMultipleRequestsWithDifferentContexts() {
        String baseUrl = "http://localhost:" + port;

        // Request 1
        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("X-User-ID", "alice");
        headers1.set("X-Session-ID", "sess-alice");
        HttpEntity<?> entity1 = new HttpEntity<>(headers1);

        ResponseEntity<String> response1 = restTemplate.exchange(
                baseUrl + "/api/user/profile",
                HttpMethod.GET,
                entity1,
                String.class
        );

        assertTrue(response1.getBody().contains("alice"));
        assertTrue(response1.getBody().contains("sess-alice"));

        // Request 2 with different context
        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("X-User-ID", "bob");
        headers2.set("X-Session-ID", "sess-bob");
        HttpEntity<?> entity2 = new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = restTemplate.exchange(
                baseUrl + "/api/user/profile",
                HttpMethod.GET,
                entity2,
                String.class
        );

        // This should contain bob's info, but might contain alice's due to ThreadLocal leak
        assertTrue(response2.getBody().contains("bob"));
        assertTrue(response2.getBody().contains("sess-bob"));
        assertFalse(response2.getBody().contains("alice"));
    }
}
