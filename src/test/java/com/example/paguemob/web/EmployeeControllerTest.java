package com.example.paguemob.web;

import com.example.paguemob.domain.Address;
import com.example.paguemob.domain.Company;
import com.example.paguemob.domain.Employee;
import com.example.paguemob.repository.CompanyRepository;
import com.example.paguemob.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void create() throws Exception {
        companyRepository.save(fixtureCompany());

        mockMvc.perform(post("/employees")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(fixtureEmployee())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void list() throws Exception {
        Company company = companyRepository.save(fixtureCompany());
        Employee employee = fixtureEmployee();
        employee.setEmployer(company);
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees")
                .accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        Company company = companyRepository.save(fixtureCompany());
        Employee fixtureEmployee = fixtureEmployee();
        fixtureEmployee.setEmployer(company);
        Employee employee = employeeRepository.save(fixtureEmployee);

        mockMvc.perform(get("/employees/{id}", employee.getId())
                .accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Employee fixtureEmployee() {
        Employee employee = new Employee();
        employee.setCpf("123.123.123-12");
        employee.setName("John Doe");
        employee.setJobTitle("Handy Man");
        employee.setEmployer(fixtureCompany());
        return employee;
    }

    private Company fixtureCompany() {
        Company company = new Company();
        company.setId(1L);
        company.setCnpj("12.123.123/0001-12");
        company.setAddress(fixtureAddress());
        company.setIndustry("Service");
        company.setName("Acme");
        company.setStreetNumber("1");
        company.setTelephone("+55 11 3040-9090");
        company.setWebsite("http://acme.com");
        return company;
    }

    private Address fixtureAddress() {
        Address address = new Address();
        address.setCep("00000-000");
        return address;
    }

}