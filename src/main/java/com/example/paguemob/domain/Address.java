package com.example.paguemob.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {
    private String bairro;
    @NotBlank
    private String cep;
    private String cidade;
    private String complemento;
    private String complemento2;
    private String end;
    private String uf;
}
