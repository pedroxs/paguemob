package com.example.paguemob.service;

import com.example.paguemob.domain.Company;
import com.example.paguemob.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CepService cepService;

    public Company create(Company company) {
        if (company.getAddress() != null && !StringUtils.isEmpty(company.getAddress().getCep())) {
            company.setAddress(cepService.findByCep(company.getAddress().getCep()));
        }
        return companyRepository.save(company);
    }

    public Page<Company> list(Pageable pageable, String name, String industry) {
        if (!StringUtils.isEmpty(name)) {
            return companyRepository.findByNameContains(pageable, name);
        } else if (!StringUtils.isEmpty(industry)) {
            return companyRepository.findByIndustry(pageable, industry);
        }
        return companyRepository.findAll(pageable);
    }

    public Company findById(Long id) {
        return companyRepository.findOne(id);
    }

}
