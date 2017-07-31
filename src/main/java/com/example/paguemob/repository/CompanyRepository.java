package com.example.paguemob.repository;

import com.example.paguemob.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findByNameContains(Pageable pageable, String name);

    Page<Company> findByIndustry(Pageable pageable, String industry);
}
