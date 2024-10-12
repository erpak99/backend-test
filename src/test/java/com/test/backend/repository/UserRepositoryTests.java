package com.test.backend.repository;


import com.test.backend.enums.UserType;
import com.test.backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user1 = new User(null, "Arda", UserType.STANDARD, 100L);
        User user2 = new User(null, "Erpak", UserType.COMPANY_ADMIN, 100L);
        User user3 = new User(null, "Andres", UserType.SUPER_USER, 101L);

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    void findByCompanyId_ShouldReturnUsersForCompany() {

        List<User> users = userRepository.findByCompanyId(100L);

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Arda")));
        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals("Erpak")));
    }

    @Test
    void findByCompanyId_ShouldReturnEmptyListForNonExistentCompany() {
        List<User> users = userRepository.findByCompanyId(999L);

        assertTrue(users.isEmpty());
    }

    @Test
    void findByCompanyId_ShouldReturnUsersForAnotherCompany() {
        List<User> users = userRepository.findByCompanyId(101L);

        assertEquals(1, users.size());
        assertEquals("Andres", users.get(0).getUsername());
    }
}
