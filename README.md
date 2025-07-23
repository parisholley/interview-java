# Java Debugging Challenge

This is a Java debugging exercise with 5 failing tests that need to be fixed.

## Prerequisites

- Java 17+
- Maven 3.6+
- IDE of your choice

## Getting Started

1. Import this project into your IDE as a Maven project
2. Run `mvn clean compile` to build the project
3. Start with Challenge 1 and work through them in order

## Challenges

### Challenge 1: Order Processing Test
**Test**: `src/test/java/com/interview/challenge1/OrderProcessingServiceTest.java`

**Issue**: Test is failing - order numbers are not behaving as expected.

### Challenge 2: Request Context Test  
**Test**: `src/test/java/com/interview/challenge2/RequestContextTest.java`

**Issue**: Test is failing - request context information appears to be leaking between HTTP requests.

### Challenge 3: Product Serialization Test
**Test**: `src/test/java/com/interview/challenge3/ProductSerializationTest.java`

**Issue**: Test is failing - JSON serialization and deserialization is not working as expected.

### Challenge 4: Logging Configuration Test
**Test**: `src/test/java/com/interview/challenge4/LoggingConfigurationTest.java`

**Issue**: Log format seems to be missing information that would be helpful for debugging.

### Challenge 5: User Repository Test
**Test**: `src/test/java/com/interview/challenge5/UserRepositoryTest.java`

**Issue**: Test will not run successfully due to database configuration issues.

## Running Tests

```bash
# Run individual tests
mvn test -Dtest=OrderProcessingServiceTest
mvn test -Dtest=RequestContextTest
mvn test -Dtest=ProductSerializationTest
mvn test -Dtest=LoggingConfigurationTest
mvn test -Dtest=UserRepositoryTest

# Run all tests
mvn test
```

## Goal

Fix the issues so that all tests pass. Focus on understanding the root cause of each problem.
