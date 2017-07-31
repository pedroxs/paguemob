package com.example.paguemob.repository;

import com.example.paguemob.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByEmployerName(Pageable pageable, String companyName);

    Page<Employee> findByJobTitle(Pageable pageable, String jobTitle);
}
