package com.interview.challenge5;

import com.interview.model.User;
import com.interview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Challenge 5: Database Testing with H2
 * 
 * This test is currently incomplete and will not run successfully.
 * The candidate needs to:
 * 
 * 1. Add H2 dependency to pom.xml:
 *    <dependency>
 *        <groupId>com.h2database</groupId>
 *        <artifactId>h2</artifactId>
 *        <scope>test</scope>
 *    </dependency>
 * 
 * 2. Configure H2 in application-test.yml:
 *    spring:
 *      datasource:
 *        url: jdbc:h2:mem:testdb
 *        driver-class-name: org.h2.Driver
 *        username: sa
 *        password: 
 *      jpa:
 *        hibernate:
 *          ddl-auto: create-drop
 *        database-platform: org.hibernate.dialect.H2Dialect
 * 
 * 3. Create schema.sql or data.sql in test/resources to set up test data
 * 
 * 4. Run the tests and verify they pass with H2 database
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        // This test will fail until H2 is properly configured
        User user = new User("testuser", "test@example.com", "Test User");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }

    @Test
    void testFindByEmailContaining() {
        User user1 = new User("user1", "john@company.com", "John Doe");
        User user2 = new User("user2", "jane@company.com", "Jane Smith");
        User user3 = new User("user3", "bob@different.org", "Bob Wilson");
        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> companyUsers = userRepository.findByEmailContaining("company");
        assertEquals(2, companyUsers.size());
    }

    @Test
    void testFindByActiveStatus() {
        User activeUser = new User("active", "active@test.com", "Active User");
        User inactiveUser = new User("inactive", "inactive@test.com", "Inactive User");
        inactiveUser.setIsActive(false);
        
        userRepository.save(activeUser);
        userRepository.save(inactiveUser);

        List<User> activeUsers = userRepository.findByActiveStatus(true);
        List<User> inactiveUsers = userRepository.findByActiveStatus(false);
        
        assertEquals(1, activeUsers.size());
        assertEquals(1, inactiveUsers.size());
        assertEquals("active", activeUsers.get(0).getUsername());
        assertEquals("inactive", inactiveUsers.get(0).getUsername());
    }

    @Test
    void testCountActiveUsers() {
        User user1 = new User("user1", "user1@test.com", "User 1");
        User user2 = new User("user2", "user2@test.com", "User 2");
        User user3 = new User("user3", "user3@test.com", "User 3");
        user3.setIsActive(false);
        
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        long activeCount = userRepository.countActiveUsers();
        assertEquals(2, activeCount);
    }
}
