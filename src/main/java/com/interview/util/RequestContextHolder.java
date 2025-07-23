package com.interview.util;

/**
 * Holds request context in ThreadLocal storage
 */
public class RequestContextHolder {
    
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();
    
    public static void setContext(RequestContext context) {
        contextHolder.set(context);
    }
    
    public static RequestContext getContext() {
        return contextHolder.get();
    }
    
    public static void clear() {
        contextHolder.remove();
    }
}
