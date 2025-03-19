package com.banco.service.impl;

import com.banco.entity.Cliente;
import com.banco.repository.ClienteRepository;
import com.banco.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public ResponseEntity<Cliente> findById(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("El cliente seleccionado no existe"));
        return ResponseEntity.ok(cliente);
    }

    @Override
    public ResponseEntity<Cliente> create(Cliente cliente) {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre(cliente.getNombre());
        cliente1.setApellidos(cliente.getApellidos());
        cliente1.setDireccion(cliente.getDireccion());
        clienteRepository.save(cliente1);
        return ResponseEntity.ok(cliente1);
    }

    @Override
    public ResponseEntity<Cliente> update(Integer id, Cliente cliente) {
        Cliente cliente1 = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("El cliente no existe"));
        cliente1.setNombre(cliente.getNombre());
        cliente1.setApellidos(cliente.getApellidos());
        cliente1.setDireccion(cliente.getDireccion());
        clienteRepository.save(cliente1);
        return ResponseEntity.ok(cliente1);
    }

    @Override
    public ResponseEntity<Cliente> delete(Integer id) {
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
