package com.example.gof.service;

import com.example.gof.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id) throws ResponseStatusException;

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
