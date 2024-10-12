package com.test.backend.service;
import com.test.backend.model.Company;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import java.util.List;
public interface TaskFetchStrategy {
    List<Task> fetchTasks(User user);


}
