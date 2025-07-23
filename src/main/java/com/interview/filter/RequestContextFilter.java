package com.interview.filter;

import com.interview.util.RequestContext;
import com.interview.util.RequestContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Challenge 2: ThreadLocal Memory Leak
 * 
 * This filter stores request context in ThreadLocal but fails to clean up properly.
 * In application servers with thread pooling, this causes values to leak between requests.
 * 
 * BUG: ThreadLocal values are not being cleared after request processing
 */
@Component
public class RequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Store request context in ThreadLocal
        String userId = httpRequest.getHeader("X-User-ID");
        String sessionId = httpRequest.getHeader("X-Session-ID");
        
        RequestContext context = new RequestContext(userId, sessionId);
        RequestContextHolder.setContext(context);
        
        try {
            chain.doFilter(request, response);
        } finally {
            // BUG: Missing cleanup! ThreadLocal values will leak to next request on same thread
            // RequestContextHolder.clear(); // <-- This line is missing!
        }
    }
}
