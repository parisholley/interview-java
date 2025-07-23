package com.interview.challenge4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Alternative LoggingService without Lombok @Slf4j annotation
 * Use this if the main LoggingService has compilation issues
 */
@Service
public class LoggingServiceAlternative {
    
    private static final Logger log = LoggerFactory.getLogger(LoggingServiceAlternative.class);
    
    public void performOperation(String operationName) {
        log.info("Starting operation: {}", operationName);
        
        try {
            // Simulate some work
            Thread.sleep(100);
            log.debug("Operation {} is in progress", operationName);
            
            // Simulate potential error
            if ("error".equals(operationName)) {
                throw new RuntimeException("Simulated error");
            }
            
            log.info("Operation {} completed successfully", operationName);
            
        } catch (Exception e) {
            log.error("Operation {} failed: {}", operationName, e.getMessage(), e);
        }
    }
    
    public void logAtDifferentLevels() {
        log.trace("This is a TRACE message");
        log.debug("This is a DEBUG message");
        log.info("This is an INFO message");
        log.warn("This is a WARN message");
        log.error("This is an ERROR message");
    }
}
