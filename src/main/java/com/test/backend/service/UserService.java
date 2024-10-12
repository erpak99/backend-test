package com.test.backend.service;

import com.test.backend.exceptions.ExceptionMessages;
import com.test.backend.exceptions.UserNotFoundException;
import com.test.backend.model.User;
import com.test.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException
                                (ExceptionMessages.USER_NOT_FOUND + " with id " + id));
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public List<User> findByCompanyId(Long companyId) {
        return userRepository.findByCompanyId(companyId);

    }

}
