package com.interview.challenge5;

import com.interview.repository.UserRepository;
import com.interview.service.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * Challenge 5: In-Memory Test Database
 * 
 * Update the application to support using an in-memory database for tests and insert seed data for this test.
 */
@SpringBootTest
@ActiveProfiles("db")
class InMemoryDatabaseTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("/data.sql")
    void canary() {
        var user = userRepository.findByUsername("foobar");

        assert(user.isPresent());
    }
}
