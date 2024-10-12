package com.test.backend.service;

import com.test.backend.exceptions.CompanyNotFoundException;
import com.test.backend.exceptions.ExceptionMessages;
import com.test.backend.exceptions.UserNotFoundException;
import com.test.backend.model.Company;
import com.test.backend.model.User;
import com.test.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(
                        () -> new CompanyNotFoundException
                                (ExceptionMessages.COMPANY_NOT_FOUND + " with id " + id));
    }

}
