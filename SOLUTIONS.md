# Java Debugging Challenge - Solutions Guide

This document contains the solutions to all challenges for interviewer reference.

## Challenge 1: Spring Bean Scope Issues

**Problem**: `CounterService` is configured as singleton, causing state to be shared between tests.

**Location**: `src/main/java/com/interview/service/CounterService.java`

**Solution**: Change the scope annotation from:
```java
@Scope("singleton")
```
to:
```java
@Scope("prototype")
```

**Evaluation Points**:
- ✅ Identifies that the issue is with Spring bean scope
- ✅ Understands difference between singleton and prototype scopes
- ✅ Recognizes why singleton causes state sharing
- ✅ Changes annotation correctly
- ⭐ **Bonus**: Explains alternative solutions like `@Scope("request")` or resetting state

---

## Challenge 2: ThreadLocal Memory Leaks

**Problem**: `RequestContextFilter` doesn't clean up ThreadLocal values after request processing.

**Location**: `src/main/java/com/interview/filter/RequestContextFilter.java`

**Solution**: Add cleanup in the finally block:
```java
try {
    chain.doFilter(request, response);
} finally {
    RequestContextHolder.clear(); // <-- Add this line
}
```

**Evaluation Points**:
- ✅ Identifies ThreadLocal memory leak issue
- ✅ Understands why cleanup is needed in servlet filters
- ✅ Knows to use finally block for cleanup
- ✅ Calls the correct cleanup method
- ⭐ **Bonus**: Explains thread pooling and memory leak implications

---

## Challenge 3: JSON Serialization Issues

**Problem**: `@JsonProperty` on fields conflicts with getter/setter names, causing JSON serialization/deserialization issues.

**Location**: `src/main/java/com/interview/model/Product.java`

**Solution Option 1** (Recommended): Use `@JsonNaming` instead of individual `@JsonProperty`:
```java
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {
    private String productId;
    private String productName;
    private BigDecimal price;
    private Boolean isActive; // Changed field name to match expected JSON
    
    public Product() {}
    
    @JsonCreator
    public Product(@JsonProperty("productId") String productId, 
                   @JsonProperty("productName") String productName,
                   @JsonProperty("price") BigDecimal price, 
                   @JsonProperty("isActive") Boolean isActive) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.isActive = isActive;
    }
    
    // standard getters/setters...
}
```

**Solution Option 2**: Move `@JsonProperty` annotations to getters/setters instead of fields:
```java
public class Product {
    private String productId;
    private String productName;
    private BigDecimal price;
    private Boolean active;
    
    @JsonProperty("product_id")
    public String getProductId() { return productId; }
    
    @JsonProperty("product_name")
    public String getProductName() { return productName; }
    
    @JsonProperty("is_active")
    public Boolean getActive() { return active; }
    
    // etc...
}
```

**Evaluation Points**:
- ✅ Identifies annotation conflict between field annotations and getter/setter names
- ✅ Understands JSON property naming issues
- ✅ Fixes deserialization by adding `@JsonCreator`
- ✅ Chooses appropriate solution approach
- ⭐ **Bonus**: Explains multiple solution options and trade-offs

---

## Challenge 4: Logback Configuration

**Problem**: Log pattern missing class name information.

**Location**: `src/main/resources/logback-spring.xml`

**Solution**: Add `%logger{36}` or `%c{36}` to the pattern:
```xml
<!-- Change from: -->
<pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level - %msg%n</pattern>

<!-- To: -->
<pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
```

Apply this change to both CONSOLE and FILE appenders.

**Evaluation Points**:
- ✅ Identifies missing class name in log output
- ✅ Locates logback configuration file
- ✅ Researches documentation or knows pattern syntax
- ✅ Updates both appenders consistently
- ⭐ **Bonus**: Explains the `{36}` parameter (class name truncation)

---

## Challenge 5: Database Testing Setup

**Problem**: H2 database dependency and configuration missing for tests.

**Solutions Required**:

1. **Add H2 dependency to `pom.xml`**:
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

2. **Configure H2 in `src/test/resources/application-test.yml`**:
```yaml
spring:
  profiles:
    active: test
    
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
    
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
    
  h2:
    console:
      enabled: true

logging:
  level:
    com.interview: DEBUG
    org.springframework.web: DEBUG
```

3. **Optional**: Create `src/test/resources/data.sql` for test data:
```sql
INSERT INTO users (username, email, full_name, created_at, is_active) 
VALUES ('testuser', 'test@example.com', 'Test User', CURRENT_TIMESTAMP, true);
```

**Evaluation Points**:
- ✅ Adds correct H2 dependency with test scope
- ✅ Configures H2 datasource properly
- ✅ Sets correct JPA/Hibernate properties for H2
- ✅ Tests run successfully after changes
- ⭐ **Bonus**: Creates SQL scripts for test data
- ⭐ **Bonus**: Explains difference between test and production database setup

---

## Interviewer Tips

### What to Look For:
1. **Debugging approach**: Do they read error messages carefully?
2. **Problem-solving process**: Do they investigate systematically?
3. **Understanding**: Do they understand the root cause or just fix symptoms?
4. **Code quality**: Are their fixes clean and maintainable?
5. **Knowledge depth**: Can they explain why their solution works?

### Common Mistakes:
1. **Challenge 1**: Trying to fix tests instead of the underlying scope issue
2. **Challenge 2**: Adding cleanup in wrong place (not in finally block)
3. **Challenge 3**: Trying to fix JSON without understanding annotation conflicts
4. **Challenge 4**: Modifying wrong files or not finding documentation
5. **Challenge 5**: Adding dependencies with wrong scope or incomplete configuration

### Follow-up Questions:
1. "How would you prevent this issue in a real project?"
2. "What are the performance implications of your solution?"
3. "Are there alternative approaches you considered?"
4. "How would you test this fix?"
5. "What would happen if we don't fix this issue?"

### Time Management:
- If a candidate is stuck for >5 minutes on one challenge, provide hints
- Focus on their debugging process, not just getting the right answer
- Allow them to move to next challenge if one is taking too long

### Red Flags:
- Making random changes without understanding the problem
- Not reading error messages or code comments
- Unable to explain their solution
- Copying solutions without understanding

### Green Flags:
- Systematic debugging approach
- Reading documentation when needed
- Understanding root causes
- Clean, maintainable fixes
- Good questions about requirements or edge cases
