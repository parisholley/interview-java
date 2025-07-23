package com.interview.challenge4;

import com.interview.service.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Challenge 4: Logging Configuration Test
 * 
 * This test generates log messages. Check the console output - 
 * the log format seems to be missing class information that could help with debugging.
 */
@SpringBootTest
@ActiveProfiles("test")
class LoggingConfigurationTest {

    @Autowired
    private LoggingService loggingService;

    @Test
    void testApplicationLogging() {
        loggingService.performOperation("test-operation");
        loggingService.performOperation("another-operation");
        loggingService.performOperation("error");
        loggingService.logAtDifferentLevels();
    }

    @Test
    void testExceptionHandling() {
        loggingService.performOperation("error");
    }
}
