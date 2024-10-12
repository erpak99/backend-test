package com.test.backend.controller;

import com.test.backend.enums.UserType;
import com.test.backend.exceptions.ExceptionMessages;
import com.test.backend.exceptions.InvalidUserTypeException;
import com.test.backend.model.Company;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import com.test.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final StandardUserTaskFetchStrategy standardFetch;
    private final UserService userService;
    private final CompanyService companyService;
    private final CompanyAdminTaskFetchStrategy companyFetch;
    private final SuperUserTaskFetchStrategy superFetch;

    @GetMapping("/standard/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (!user.getUserType().equals(UserType.STANDARD)) {
            throw new InvalidUserTypeException(ExceptionMessages.INVALID_USER_TYPE);
        }
        List<Task> tasks = standardFetch.fetchTasks(user);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/companyadmin/{companyId}")
    public ResponseEntity<List<Task>> getTasksByCompanyId(@PathVariable Long companyId) {
        List<User> users = userService.findByCompanyId(companyId);
        List<Task> tasks = new ArrayList<>();

        for (User user : users) {
            tasks.addAll(companyFetch.fetchTasks(user));
        }

        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/superuser")
    public List<Task> getTasksForSuperUsers() {
        return superFetch.fetchTasks();
    }

}
