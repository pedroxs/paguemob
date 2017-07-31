package com.example.paguemob.web;

import com.example.paguemob.domain.Address;
import com.example.paguemob.domain.Company;
import com.example.paguemob.repository.CompanyRepository;
import com.example.paguemob.repository.EmployeeRepository;
import com.example.paguemob.service.CepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CepService cepService;

    @Before
    public void setUp() throws Exception {
        companyRepository.deleteAll();
    }

    @Test
    public void create() throws Exception {
        BDDMockito.given(cepService.findByCep(Matchers.anyString())).willReturn(fixtureAddress());

        mockMvc.perform(post("/companies")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(fixtureCompany())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void list() throws Exception {
        companyRepository.save(fixtureCompany());

        mockMvc.perform(get("/companies").accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        Company company = companyRepository.save(fixtureCompany());

        mockMvc.perform(get("/companies/{id}", company.getId())
                .accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public Company fixtureCompany() {
        Company company = new Company();
        company.setCnpj("12.123.123/0001-12");
        company.setAddress(fixtureAddress());
        company.setIndustry("Service");
        company.setName("Acme");
        company.setStreetNumber("1");
        company.setTelephone("+55 11 3040-9090");
        company.setWebsite("http://acme.com");
        return company;
    }

    public Address fixtureAddress() {
        Address address = new Address();
        address.setCep("00000-000");
        return address;
    }

}