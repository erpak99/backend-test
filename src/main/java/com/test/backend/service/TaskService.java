package com.test.backend.service;

import com.test.backend.enums.UserType;
import com.test.backend.exceptions.ExceptionMessages;
import com.test.backend.exceptions.InvalidUserTypeException;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import com.test.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private final Map<UserType, TaskFetchStrategy> strategies;

    public TaskService(TaskRepository taskRepository) {
        strategies = Map.of(
                UserType.SUPER_USER, new SuperUserTaskFetchStrategy(taskRepository),
                UserType.COMPANY_ADMIN, new CompanyAdminTaskFetchStrategy(taskRepository),
                UserType.STANDARD, new StandardUserTaskFetchStrategy(taskRepository)
        );
    }

    public List<Task> getTasks(User currentUser) {
        return Optional.ofNullable(strategies.get(currentUser.getUserType()))
                .orElseThrow(() -> new InvalidUserTypeException(ExceptionMessages.INVALID_USER_TYPE))
                .fetchTasks(currentUser);
    }


}
