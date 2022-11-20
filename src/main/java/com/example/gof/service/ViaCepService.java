package com.example.gof.service;

import com.example.gof.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url="https://viacep.com.br/ws")
public interface ViaCepService {
    @GetMapping(value = "/{cep}/json/")
    Endereco consultarCEP(@PathVariable("cep") String cep);
}
