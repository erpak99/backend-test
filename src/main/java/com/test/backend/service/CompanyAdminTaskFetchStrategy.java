package com.test.backend.service;

import com.test.backend.model.Company;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import com.test.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyAdminTaskFetchStrategy implements TaskFetchStrategy {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> fetchTasks(User user) {

        return taskRepository.findByCompanyId(user.getCompanyId());

    }

}