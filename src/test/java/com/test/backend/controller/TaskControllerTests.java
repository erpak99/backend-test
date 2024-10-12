package com.test.backend.controller;

import com.test.backend.enums.UserType;
import com.test.backend.exceptions.InvalidUserTypeException;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import com.test.backend.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TaskControllerTests {

    @Mock
    private StandardUserTaskFetchStrategy standardFetch;

    @Mock
    private UserService userService;

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyAdminTaskFetchStrategy companyFetch;

    @Mock
    private SuperUserTaskFetchStrategy superFetch;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasksByUserId_ShouldReturnTasksForStandardUser() {
        User standardUser = new User(1L, "John Doe", UserType.STANDARD, 100L);
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1", 1L, 100L),
                new Task(2L, "Task 2", "Description 2", 1L, 100L)
        );
        when(userService.findById(1L)).thenReturn(standardUser);
        when(standardFetch.fetchTasks(standardUser)).thenReturn(mockTasks);

        ResponseEntity<List<Task>> response = taskController.getTasksByUserId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockTasks, response.getBody());
    }

    @Test
    void getTasksByUserId_ShouldThrowExceptionForNonStandardUser() {
        User adminUser = new User(2L, "Jane Doe", UserType.COMPANY_ADMIN, 101L);
        when(userService.findById(2L)).thenReturn(adminUser);

        assertThrows(InvalidUserTypeException.class, () -> taskController.getTasksByUserId(2L));
    }

    @Test
    void getTasksByCompanyId_ShouldReturnTasksForCompanyAdmin() {
        User companyUser = new User(1L, "John Doe", UserType.COMPANY_ADMIN, 100L);
        List<User> mockUsers = Collections.singletonList(companyUser);
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1", 1L, 100L),
                new Task(2L, "Task 2", "Description 2", 1L, 100L)
        );
        when(userService.findByCompanyId(100L)).thenReturn(mockUsers);
        when(companyFetch.fetchTasks(companyUser)).thenReturn(mockTasks);

        ResponseEntity<List<Task>> response = taskController.getTasksByCompanyId(100L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockTasks, response.getBody());
    }

    @Test
    void getTasksForSuperUsers_ShouldReturnAllTasks() {
        List<Task> mockTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1", 1L, 100L),
                new Task(2L, "Task 2", "Description 2", 2L, 101L),
                new Task(3L, "Task 3", "Description 3", 3L, 102L)
        );
        when(superFetch.fetchTasks()).thenReturn(mockTasks);

        List<Task> tasks = taskController.getTasksForSuperUsers();

        assertEquals(mockTasks.size(), tasks.size());
        assertEquals(mockTasks, tasks);
    }
}
