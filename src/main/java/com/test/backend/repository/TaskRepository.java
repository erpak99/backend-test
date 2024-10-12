package com.test.backend.repository;

import com.test.backend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByCompanyId(Long companyId);
    List<Task> findByUserId(Long userId);


}
