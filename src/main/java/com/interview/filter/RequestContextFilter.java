package com.interview.filter;

import com.interview.util.RequestContext;
import com.interview.util.RequestContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter that stores request context information.
 */
@Component
public class RequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String userId = httpRequest.getHeader("X-User-ID");
        String sessionId = httpRequest.getHeader("X-Session-ID");
        
        RequestContext context = new RequestContext(userId, sessionId);
        RequestContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}
