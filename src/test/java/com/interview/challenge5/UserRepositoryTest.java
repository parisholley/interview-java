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
 * Challenge 5: User Repository Test
 * 
 * This test will not run successfully due to database configuration issues.
 * The main application is configured for MySQL, but the tests need to run
 * with a different database setup.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
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
