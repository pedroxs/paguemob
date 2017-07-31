package com.example.paguemob.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    private String cnpj;

    @NotNull
    @Valid
    @Embedded
    private Address address;
    private String streetNumber;
    private String telephone;
    private String website;
    private String industry;
}
