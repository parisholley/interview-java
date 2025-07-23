package com.interview.controller;

import com.interview.util.RequestContext;
import com.interview.util.RequestContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that retrieves user information based on ThreadLocal context
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @GetMapping("/profile")
    public String getUserProfile() {
        RequestContext context = RequestContextHolder.getContext();
        
        if (context == null) {
            return "No user context available";
        }
        
        return String.format("User Profile - ID: %s, Session: %s", 
                context.getUserId(), context.getSessionId());
    }
    
    @GetMapping("/context")
    public String getRequestContext() {
        RequestContext context = RequestContextHolder.getContext();
        return context != null ? context.toString() : "No context";
    }
}
