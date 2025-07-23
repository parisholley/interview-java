package com.interview.challenge3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Challenge 3: Product Serialization Test
 * 
 * Tests JSON serialization and deserialization of Product objects.
 */
@SpringBootTest
@ActiveProfiles("test")
class ProductSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testProductToJson() throws Exception {
        Product product = new Product("PROD-123", "Test Widget", new BigDecimal("29.99"), true);
        
        String json = objectMapper.writeValueAsString(product);
        System.out.println("Serialized JSON: " + json);
        
        assertTrue(json.contains("\"product_id\":\"PROD-123\""));
        assertTrue(json.contains("\"product_name\":\"Test Widget\""));
        assertTrue(json.contains("\"price\":29.99"));
        assertTrue(json.contains("\"is_active\":true"));
    }

    @Test
    void testJsonToProduct() throws Exception {
        String json = """
                {
                    "product_id": "PROD-456",
                    "product_name": "Another Widget",
                    "price": 19.99,
                    "is_active": false
                }
                """;
        
        Product product = objectMapper.readValue(json, Product.class);
        
        assertEquals("PROD-456", product.getProductId());
        assertEquals("Another Widget", product.getProductName());
        assertEquals(new BigDecimal("19.99"), product.getPrice());
        assertEquals(false, product.getActive());
    }

    @Test
    void testProductJsonConversion() throws Exception {
        Product original = new Product("PROD-789", "Round Trip Widget", new BigDecimal("15.50"), true);
        
        String json = objectMapper.writeValueAsString(original);
        Product deserialized = objectMapper.readValue(json, Product.class);
        
        assertEquals(original.getProductId(), deserialized.getProductId());
        assertEquals(original.getProductName(), deserialized.getProductName());
        assertEquals(original.getPrice(), deserialized.getPrice());
        assertEquals(original.getActive(), deserialized.getActive());
    }
}
