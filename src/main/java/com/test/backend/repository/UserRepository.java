package com.test.backend.repository;

import com.test.backend.model.Task;
import com.test.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByCompanyId(Long companyId);

}
