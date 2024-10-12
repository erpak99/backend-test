package com.test.backend.controller;

import com.test.backend.enums.UserType;
import com.test.backend.model.User;
import com.test.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        List<User> mockUsers = Arrays.asList(
                new User(1L, "Arda", UserType.STANDARD, 100L),
                new User(2L, "Erpak", UserType.COMPANY_ADMIN, 101L)
        );
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUsers, response.getBody());
    }
}
