package com.test.backend.repository;

import com.test.backend.model.Task;
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
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();

        Task task1 = new Task(null, "Task 1", "Description 1", 1L, 100L);
        Task task2 = new Task(null, "Task 2", "Description 2", 2L, 101L);
        Task task3 = new Task(null, "Task 3", "Description 3", 3L, 100L);
        taskRepository.saveAll(List.of(task1, task2, task3));
    }

    @Test
    void findByCompanyId_ShouldReturnTasksForCompany() {
        List<Task> tasks = taskRepository.findByCompanyId(100L);

        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(tasks.stream().anyMatch(task -> task.getTitle().equals("Task 3")));
    }

    @Test
    void findByUserId_ShouldReturnTasksForUser() {
        List<Task> tasks = taskRepository.findByUserId(1L);

        assertEquals(1, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
    }

    @Test
    void findByCompanyId_ShouldReturnEmptyListForNonExistentCompany() {
        List<Task> tasks = taskRepository.findByCompanyId(999L);

        assertTrue(tasks.isEmpty());
    }
}
