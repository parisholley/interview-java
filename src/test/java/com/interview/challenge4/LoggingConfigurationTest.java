package com.interview.challenge4;

import com.interview.service.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Challenge 4: Logback Configuration Issue
 * 
 * This test generates log messages to demonstrate the missing class name issue.
 * When you run this test, check the console output and log files.
 * 
 * CURRENT OUTPUT: Missing class names in log messages
 * 2024-01-15 10:30:45 [main] INFO  - Starting operation: test
 * 
 * EXPECTED OUTPUT: Should include calling class name
 * 2024-01-15 10:30:45 [main] INFO  c.i.challenge4.LoggingService - Starting operation: test
 * 
 * TASK: Update logback-spring.xml to include class names in log pattern
 * SOLUTION: Add %logger{36} or %c{36} to the pattern
 * DOCUMENTATION: https://logback.qos.ch/manual/layouts.html
 */
@SpringBootTest
class LoggingConfigurationTest {

    @Autowired
    private LoggingService loggingService;

    @Test
    void testLoggingOutput() {
        System.out.println("=== Challenge 4: Check log output for missing class names ===");
        System.out.println("Current logs are missing class names. Fix logback-spring.xml pattern.");
        System.out.println("Expected pattern should include %logger{36} or %c{36}");
        System.out.println("Documentation: https://logback.qos.ch/manual/layouts.html");
        System.out.println("===============================================================");
        
        loggingService.performOperation("test-operation");
        loggingService.performOperation("another-operation");
        loggingService.performOperation("error"); // This will generate error logs
        loggingService.logAtDifferentLevels();
        
        System.out.println("Check the log output above - class names should be visible after fixing logback-spring.xml");
    }

    @Test
    void testErrorLogging() {
        System.out.println("=== Testing error logging - should show class name ===");
        loggingService.performOperation("error");
    }
}
