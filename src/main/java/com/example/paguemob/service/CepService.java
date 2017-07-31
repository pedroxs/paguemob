package com.example.paguemob.service;

import com.example.paguemob.correios.*;
import com.example.paguemob.domain.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CepService {

    private final AtendeClienteService correiosService = new AtendeClienteService();

    public Address findByCep(String cep) {
        EnderecoERP endereco;
        Address address = new Address();
        address.setCep(cep);

        try {
            endereco = correiosService
                    .getAtendeClientePort()
                    .consultaCEP(cep.replaceAll("-", ""));
            BeanUtils.copyProperties(endereco, address);
        } catch (SQLException_Exception | SigepClienteException e) {
            log.error("Error retrieving CEP from Correios", e);
        }

        return address;
    }
}
