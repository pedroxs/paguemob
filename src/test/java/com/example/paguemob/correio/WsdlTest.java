package com.example.paguemob.correio;

import com.example.paguemob.correios.AtendeClienteService;
import com.example.paguemob.correios.EnderecoERP;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Objects;

public class WsdlTest {

    @Test
    public void of() throws Exception {
        AtendeClienteService service = new AtendeClienteService();
        EnderecoERP enderecoERP = service.getAtendeClientePort().consultaCEP("05706280");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(enderecoERP));
    }
}
