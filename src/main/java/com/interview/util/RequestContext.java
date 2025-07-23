package com.interview.util;

/**
 * Request context information stored in ThreadLocal
 */
public class RequestContext {
    private final String userId;
    private final String sessionId;
    
    public RequestContext(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    @Override
    public String toString() {
        return String.format("RequestContext{userId='%s', sessionId='%s'}", userId, sessionId);
    }
}
