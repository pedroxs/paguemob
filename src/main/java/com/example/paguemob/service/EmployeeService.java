package com.example.paguemob.service;

import com.example.paguemob.domain.Employee;
import com.example.paguemob.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Page<Employee> list(Pageable pageable, String companyName, String jobTitle) {
        if (!StringUtils.isEmpty(companyName)) {
            return employeeRepository.findByEmployerName(pageable, companyName);
        } else if (!StringUtils.isEmpty(jobTitle)) {
            return employeeRepository.findByJobTitle(pageable, jobTitle);
        }
        return employeeRepository.findAll(pageable);
    }

    public Employee findById(Long id) {
        return employeeRepository.findOne(id);
    }
}
