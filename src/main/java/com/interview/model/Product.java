package com.interview.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Challenge 3: Serialization/Deserialization Issues
 * 
 * This class has annotation conflicts that cause JSON serialization/deserialization 
 * to fail or produce unexpected results.
 * 
 * Expected JSON format:
 * {
 *   "product_id": "123",
 *   "product_name": "Widget",
 *   "price": 29.99,
 *   "is_active": true
 * }
 * 
 * BUG: @JsonProperty on fields conflicts with getter/setter names
 * BUG: Missing @JsonCreator for deserialization
 * BUG: Field name 'active' doesn't match expected JSON property 'is_active'
 */
public class Product {
    
    @JsonProperty("product_id")
    private String productId;
    
    @JsonProperty("product_name") 
    private String productName;
    
    private BigDecimal price;
    
    // BUG: Field name doesn't match expected JSON property name
    @JsonProperty("is_active")
    private Boolean active;  // Should be 'isActive' or use @JsonProperty on getter/setter
    
    // No-args constructor
    public Product() {}
    
    // FIXED: Added @JsonCreator for proper deserialization
    @JsonCreator
    public Product(@JsonProperty("product_id") String productId, 
                   @JsonProperty("product_name") String productName,
                   @JsonProperty("price") BigDecimal price, 
                   @JsonProperty("is_active") Boolean active) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.active = active;
    }
    
    // Getters and setters - these names conflict with @JsonProperty field annotations
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
}
