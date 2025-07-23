package com.interview.challenge5;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Challenge 5: Database Testing Challenge
 * 
 * This repository is configured for MySQL in production.
 * The challenge is to write tests using H2 in-memory database.
 * 
 * Candidate needs to:
 * 1. Add H2 dependency to pom.xml
 * 2. Configure test profile to use H2 instead of MySQL  
 * 3. Create SQL script to set up test data
 * 4. Write integration tests for the repository methods
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    List<User> findByEmailContaining(String emailPart);
    
    @Query("SELECT u FROM User u WHERE u.isActive = :active ORDER BY u.createdAt DESC")
    List<User> findByActiveStatus(@Param("active") Boolean active);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();
}
