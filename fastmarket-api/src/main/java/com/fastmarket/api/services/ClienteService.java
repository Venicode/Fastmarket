package com.fastmarket.api.services;

import com.fastmarket.api.domains.Cliente;
import com.fastmarket.api.repositories.ClienteRepository;
import com.fastmarket.api.dtos.ClienteDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(ClienteDto clienteDto) throws Exception{
       Cliente newCliente = new Cliente();
       newCliente.setCpf(clienteDto.cpf());
       newCliente.setEmail(clienteDto.email());
       newCliente.setTelefone(clienteDto.telefone());
       newCliente.setPrimeiroNome(clienteDto.primeiroNome());
       newCliente.setUltimoNome(clienteDto.ultimoNome());
       newCliente.setAtivo(clienteDto.ativo());

       this.clienteRepository.save(newCliente);
       return newCliente;
    }
    public Cliente updateCliente(ClienteDto clienteDto, Long id) throws Exception{
        Cliente newCliente =findClienteById(id);
        newCliente.setCpf(clienteDto.cpf());
        newCliente.setEmail(clienteDto.email());
        newCliente.setTelefone(clienteDto.telefone());
        newCliente.setPrimeiroNome(clienteDto.primeiroNome());
        newCliente.setUltimoNome(clienteDto.ultimoNome());
        newCliente.setAtivo(clienteDto.ativo());
        this.clienteRepository.save(newCliente);
        return newCliente;
    }
    public Cliente findClienteById(Long id) throws Exception{
        return this.clienteRepository.findClienteById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente not found."));

    }

    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clienteList = this.clienteRepository.findAll();
        return new ResponseEntity<>(clienteList, HttpStatus.OK);
    }
    public void disableCliente(Long id){
        this.clienteRepository.disableCliente(id);
    }
}
