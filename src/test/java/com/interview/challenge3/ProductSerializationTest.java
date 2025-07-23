package com.interview.challenge3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Challenge 3: JSON Serialization/Deserialization Issues
 * 
 * This test demonstrates the problems with Lombok @Data annotation conflicting
 * with Jackson @JsonProperty annotations.
 * 
 * ISSUES:
 * 1. @Data generates getters/setters that don't match @JsonProperty names
 * 2. Missing @JsonCreator for deserialization
 * 3. Field name 'active' vs expected JSON property 'is_active'
 * 
 * SOLUTIONS:
 * 1. Use @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) instead of individual @JsonProperty
 * 2. Or use @JsonProperty on getters/setters instead of fields
 * 3. Add @JsonCreator constructor
 * 4. Fix field naming to match expected JSON
 */
@SpringBootTest
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class ProductSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testProductSerialization() throws Exception {
        Product product = new Product("PROD-123", "Test Widget", new BigDecimal("29.99"), true);
        
        String json = objectMapper.writeValueAsString(product);
        System.out.println("Serialized JSON: " + json);
        
        // These assertions will FAIL due to annotation conflicts
        assertTrue(json.contains("\"product_id\":\"PROD-123\""));
        assertTrue(json.contains("\"product_name\":\"Test Widget\""));
        assertTrue(json.contains("\"price\":29.99"));
        assertTrue(json.contains("\"is_active\":true"));
    }

    @Test
    void testProductDeserialization() throws Exception {
        String json = """
                {
                    "product_id": "PROD-456",
                    "product_name": "Another Widget",
                    "price": 19.99,
                    "is_active": false
                }
                """;
        
        // This will FAIL due to missing @JsonCreator and annotation conflicts
        Product product = objectMapper.readValue(json, Product.class);
        
        assertEquals("PROD-456", product.getProductId());
        assertEquals("Another Widget", product.getProductName());
        assertEquals(new BigDecimal("19.99"), product.getPrice());
        assertEquals(false, product.getActive());
    }

    @Test
    void testRoundTripSerialization() throws Exception {
        Product original = new Product("PROD-789", "Round Trip Widget", new BigDecimal("15.50"), true);
        
        // Serialize to JSON
        String json = objectMapper.writeValueAsString(original);
        
        // Deserialize back to object
        Product deserialized = objectMapper.readValue(json, Product.class);
        
        // These should be equal but will FAIL due to serialization issues
        assertEquals(original.getProductId(), deserialized.getProductId());
        assertEquals(original.getProductName(), deserialized.getProductName());
        assertEquals(original.getPrice(), deserialized.getPrice());
        assertEquals(original.getActive(), deserialized.getActive());
    }
}
