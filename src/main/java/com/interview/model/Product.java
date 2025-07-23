package com.interview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Product model class for JSON serialization/deserialization.
 * 
 * Expected JSON format:
 * {
 *   "product_id": "123",
 *   "product_name": "Widget", 
 *   "price": 29.99,
 *   "is_active": true
 * }
 */
public class Product {
    
    @JsonProperty("product_id")
    private String productId;
    
    @JsonProperty("product_name") 
    private String productName;
    
    private BigDecimal price;
    
    @JsonProperty("is_active")
    private Boolean active;
    
    // public Product() {}
    public Product(String productId, String productName, BigDecimal price, Boolean active) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.active = active;
    }
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
