# Java Debugging Challenge

This is a Java debugging exercise with 5 challenges that need to be fixed.

## Prerequisites

- Java 17+
- Maven 3.6+
- IDE of your choice

## Getting Started

1. Import this project into your IDE as a Maven project
2. Run `mvn clean compile` to build the project
3. Start with Challenge 1 and work through them in order

## Challenges and Solutions

### Challenge 1: Order Processing Test
**Test**: `src/test/java/com/interview/challenge1/OrderProcessingServiceTest.java`

**Issue**: Test is failing - order numbers are not behaving as expected. Order numbers should start from 1 for each new HTTP request, but they continue incrementing across requests.

**Root Cause**: The `OrderProcessingService` was using singleton scope by default, causing the counter to persist across HTTP requests.

**Solution**: Changed the `OrderProcessingService` scope to `request` scope:
```java
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderProcessingService {
    // ... implementation
}
```

This ensures each HTTP request gets a fresh instance of the service with a new counter starting from 1.

### Challenge 2: Request Context Test  
**Test**: `src/test/java/com/interview/challenge2/RequestContextTest.java`

**Issue**: Test is failing - request context information appears to be leaking between HTTP requests. ThreadLocal values from previous requests are contaminating new requests.

**Root Cause**: The `RequestContextFilter` was storing request context in ThreadLocal but not cleaning up after request processing. In application servers with thread pooling, this causes values to leak between requests.

**Solution**: Added proper ThreadLocal cleanup in the filter's finally block:
```java
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    // ... setup code
    try {
        chain.doFilter(request, response);
    } finally {
        // FIXED: Added ThreadLocal cleanup to prevent memory leaks
        RequestContextHolder.clear();
    }
}
```

### Challenge 3: Product Serialization Test
**Test**: `src/test/java/com/interview/challenge3/ProductSerializationTest.java`

**Issue**: Test is failing - JSON serialization and deserialization is not working as expected. The JSON uses snake_case field names but the Java model doesn't handle this properly.

**Root Cause**: Missing Jackson annotations for proper JSON field mapping and constructor-based deserialization.

**Solution**: Added proper Jackson annotations to the `Product` class:
```java
public class Product {
    @JsonProperty("product_id")
    private String productId;
    
    @JsonProperty("product_name") 
    private String productName;
    
    private BigDecimal price;
    
    @JsonProperty("is_active")
    private Boolean active;
    
    // Added @JsonCreator for proper deserialization
    @JsonCreator
    public Product(@JsonProperty("product_id") String productId, 
                   @JsonProperty("product_name") String productName,
                   @JsonProperty("price") BigDecimal price, 
                   @JsonProperty("is_active") Boolean active) {
        // ... constructor implementation
    }
}
```

### Challenge 4: Logging Configuration Test
**Test**: `src/test/java/com/interview/challenge4/LoggingConfigurationTest.java`

**Issue**: Log format seems to be missing information that would be helpful for debugging - specifically the class name information.

**Root Cause**: The logback configuration pattern was missing the logger name (`%logger`) which shows the class that generated the log message.

**Solution**: Updated the logback pattern in `src/main/resources/logback-spring.xml`:
```xml
<!-- FIXED: Added class name to pattern -->
<pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
```

This adds the logger name (typically the class name) to help with debugging.

### Challenge 5: In-Memory Database Test
**Test**: `src/test/java/com/interview/challenge5/InMemoryDatabaseTest.java`

**Issue**: The application is configured to use MySQL, but tests fail due to missing database connection. Need to configure the application to use an in-memory H2 database for testing and seed it with test data.

**Root Cause**: No in-memory database configuration for tests and no seed data for the test that expects a user with username "foobar".

**Solution**: 
1. **Configured H2 in-memory database** in `src/test/resources/application-db.yml`:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: create-drop
    database-platform: org.hibernate.dialect.H2Dialect
```

2. **Created seed data file** `src/test/resources/data.sql`:
```sql
INSERT INTO users (username, email, full_name, created_at, is_active) VALUES
('foobar', 'foobar@example.com', 'Foobar User', NOW(), true);
```

3. **Used @Sql annotation** to load seed data:
```java
@Test
@Sql("/data.sql")
void canary() {
    var user = userRepository.findByUsername("foobar");
    assert(user.isPresent());
}
```

## Running Tests

```bash
# Run individual tests
mvn test -Dtest=OrderProcessingServiceTest
mvn test -Dtest=RequestContextTest
mvn test -Dtest=ProductSerializationTest
mvn test -Dtest=LoggingConfigurationTest
mvn test -Dtest=InMemoryDatabaseTest

# Run all tests
mvn test
```

## Key Learning Points

1. **Bean Scoping**: Understanding when to use singleton vs request scope for Spring beans
2. **ThreadLocal Management**: Proper cleanup of ThreadLocal variables to prevent memory leaks
3. **Jackson Configuration**: Using annotations for custom JSON field mapping and deserialization
4. **Logging Configuration**: Configuring useful log patterns for debugging
5. **Test Database Setup**: Using in-memory databases and seed data for reliable testing

## Goal

All tests should now pass. Each solution addresses a common real-world issue that developers encounter in Spring Boot applications.
