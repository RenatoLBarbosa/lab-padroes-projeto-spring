package com.example.gof.service.impl;

import com.example.gof.config.ClienteNaoExisteException;
import com.example.gof.model.Cliente;
import com.example.gof.model.ClienteRepository;
import com.example.gof.model.Endereco;
import com.example.gof.model.EnderecoRepository;
import com.example.gof.service.ClienteService;
import com.example.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) throws ResponseStatusException {
        //valida se o cliente existe
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()){
            return cliente.get();
        }
        return cliente.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente com id "+id+" não existente!"));
    }

    private void salvarClienteComCep(Cliente cliente) {
        // valida se o endereco existe pelo cep do cliente
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(()->{
            // Caso não exista ira persistir o retorno do viacep
            Endereco novoendereco = viaCepService.consultarCEP(cep);
            enderecoRepository.save(novoendereco);
            return novoendereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
