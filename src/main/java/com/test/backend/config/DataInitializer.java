package com.test.backend.config;

import com.test.backend.enums.UserType;
import com.test.backend.model.Company;
import com.test.backend.model.Task;
import com.test.backend.model.User;
import com.test.backend.repository.CompanyRepository;
import com.test.backend.repository.TaskRepository;
import com.test.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CompanyRepository companyRepository;


    @Override
    public void run(String... args) throws Exception {


        Company company1 = new Company(1L, "Company A");
        Company company2 = new Company(2L, "Company B");
        Company company3 = new Company(3L, "Company C");
        Company company4 = new Company(4L, "Company D");
        companyRepository.saveAll(Arrays.asList(company1, company2));

        User superUser = new User(1L, "SUPER USER", UserType.SUPER_USER, null);
        User companyAdmin1 = new User(2L, "ADMIN COMPANY 1", UserType.COMPANY_ADMIN, company1.getId());
        User companyAdmin2 = new User(3L, "ADMIN COMPANY 2", UserType.COMPANY_ADMIN, company2.getId());
        User companyAdmin3 = new User(4L, "ADMIN COMPANY 3", UserType.COMPANY_ADMIN, company1.getId());
        User companyAdmin4 = new User(5L, "ADMIN COMPANY 4", UserType.COMPANY_ADMIN, company2.getId());
        User standardUser1 = new User(6L, "STANDARD USER 1", UserType.STANDARD, company1.getId());
        User standardUser2 = new User(7L, "STANDARD USER 2", UserType.STANDARD, company1.getId());
        User standardUser3 = new User(8L, "STANDARD USER 3", UserType.STANDARD, company2.getId());
        userRepository.saveAll(Arrays.asList(superUser, companyAdmin1, companyAdmin2, standardUser1, standardUser2, standardUser3));

        List<Task> tasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1", standardUser1.getId(), company1.getId()),
                new Task(2L, "Task 2", "Description 2", standardUser2.getId(), company1.getId()),
                new Task(3L, "Task 3", "Description 3", standardUser3.getId(), company1.getId()),
                new Task(4L, "Task 4", "Description 4", companyAdmin1.getId(), company1.getId()),
                new Task(5L, "Task 5", "Description 5", companyAdmin2.getId(), company2.getId()),
                new Task(6L, "Task 6", "Description 6", companyAdmin3.getId(), company2.getId()),
                new Task(7L, "Task 7", "Description 7", companyAdmin4.getId(), company3.getId()),
                new Task(8L, "Task 8", "Description 8", companyAdmin4.getId(), company4.getId()),
                new Task(9L, "Task 9", "Description 9", superUser.getId(), company2.getId())
        );
        taskRepository.saveAll(tasks);

    }
}
