# Java Debugging Interview Challenge

Welcome to the Java debugging interview challenge! This project contains 5 progressive challenges designed to test your debugging and problem-solving skills with Java, Spring Boot, and Maven.

## Overview

Each challenge represents a common real-world issue that developers encounter. The challenges are designed to be completed in order, as some may depend on fixes from previous challenges.

## Prerequisites

- Java 17+
- Maven 3.6+
- IDE of your choice

## Getting Started

1. Clone or download this project
2. Import it into your IDE as a Maven project
3. Run `mvn clean compile` to ensure the project builds
4. Start with Challenge 1 and work through them in order

## Challenges

### Challenge 1: Spring Bean Scope Issues ❌
**Location**: `src/test/java/com/interview/challenge1/OrderProcessingServiceTest.java`

**Problem**: The test `OrderProcessingServiceTest` is failing because counter values are being shared between test methods unexpectedly.

**What to investigate**:
- Run the test and observe the failure
- Look at the `CounterService` implementation
- Understand why the counter state is being shared

**Expected skill demonstration**: Understanding of Spring bean scopes and lifecycle

---

### Challenge 2: ThreadLocal Memory Leaks ❌
**Location**: `src/test/java/com/interview/challenge2/RequestContextTest.java`

**Problem**: The `RequestContextTest` is failing because request context information is leaking between HTTP requests.

**What to investigate**:
- Run the test and observe how context from one request appears in another
- Examine the `RequestContextFilter` implementation
- Understand the ThreadLocal cleanup issue

**Expected skill demonstration**: Understanding of ThreadLocal usage and memory leak prevention

---

### Challenge 3: JSON Serialization Issues ❌
**Location**: `src/test/java/com/interview/challenge3/ProductSerializationTest.java`

**Problem**: The `ProductSerializationTest` is failing because JSON serialization/deserialization isn't working as expected.

**What to investigate**:
- Run the test and examine the actual vs expected JSON format
- Look at the `Product` class annotations
- Understand the conflict between Lombok and Jackson annotations

**Expected skill demonstration**: Understanding of JSON serialization libraries and annotation interactions

---

### Challenge 4: Logback Configuration ⚠️
**Location**: `src/test/java/com/interview/challenge4/LoggingConfigurationTest.java`

**Problem**: Log messages are missing class name information, making debugging difficult.

**What to investigate**:
- Run the test and examine the log output
- Look at `src/main/resources/logback-spring.xml`
- Research Logback documentation to find the correct pattern

**Expected skill demonstration**: Ability to read documentation and configure logging frameworks

**Documentation**: https://logback.qos.ch/manual/layouts.html

---

### Challenge 5: Database Testing Setup ❌
**Location**: `src/test/java/com/interview/challenge5/UserRepositoryTest.java`

**Problem**: The `UserRepositoryTest` won't run because H2 database is not properly configured for testing.

**What to investigate**:
- Try to run the test and observe the failure
- The main application uses MySQL, but tests should use H2
- Set up H2 database configuration for the test profile

**Tasks**:
1. Add H2 dependency to `pom.xml`
2. Configure H2 database in `src/test/resources/application-test.yml`
3. Ensure tests run successfully with H2
4. Optionally create SQL scripts for test data

**Expected skill demonstration**: Understanding of database configuration and test setup

## Running Tests

To run individual challenge tests:

```bash
# Challenge 1
mvn test -Dtest=OrderProcessingServiceTest

# Challenge 2  
mvn test -Dtest=RequestContextTest

# Challenge 3
mvn test -Dtest=ProductSerializationTest

# Challenge 4
mvn test -Dtest=LoggingConfigurationTest

# Challenge 5
mvn test -Dtest=UserRepositoryTest

# Run all tests
mvn test
```

## Success Criteria

- All tests should pass after fixing the issues
- Code should follow Spring Boot best practices
- Solutions should address the root cause, not just make tests pass
- Demonstrate understanding of the underlying concepts

## Tips

1. **Read the error messages carefully** - they often contain clues about what's wrong
2. **Look at the comments in the code** - they provide hints about the issues
3. **Use your IDE's debugging capabilities** - set breakpoints and step through the code
4. **Research documentation** - especially for Challenge 4, you'll need to look up Logback patterns
5. **Think about the lifecycle** - many issues relate to object lifecycle and state management

## Time Expectation

Each challenge should take approximately 10-15 minutes to debug and fix, for a total of about 60-75 minutes.

## Need Help?

If you get stuck:
1. Explain your debugging process so far
2. Show what you've discovered about the issue
3. Ask specific questions about concepts you're unsure about

Good luck! 🚀
