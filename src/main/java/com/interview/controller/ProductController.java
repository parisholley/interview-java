package com.interview.controller;

import com.interview.model.Product;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller for product operations that exposes the serialization issues
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // This will fail when trying to deserialize JSON due to annotation issues
        return product;
    }
    
    @GetMapping("/sample")
    public Product getSampleProduct() {
        // This will produce JSON that doesn't match expected format
        return new Product("PROD-001", "Sample Widget", new BigDecimal("29.99"), true);
    }
    
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return new Product(id, "Test Product", new BigDecimal("19.99"), false);
    }
}
