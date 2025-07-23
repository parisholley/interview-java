package com.interview.challenge4;

import com.interview.service.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Challenge 4: Logging Configuration Test
 * 
 * This test generates log messages. Check the console output - 
 * the log format seems to be missing some information that would
 * be helpful for debugging.
 */
@SpringBootTest
class LoggingConfigurationTest {

    @Autowired
    private LoggingService loggingService;

    @Test
    void testLoggingOutput() {
        loggingService.performOperation("test-operation");
        loggingService.performOperation("another-operation");
        loggingService.performOperation("error");
        loggingService.logAtDifferentLevels();
    }

    @Test
    void testErrorLogging() {
        loggingService.performOperation("error");
    }
}
