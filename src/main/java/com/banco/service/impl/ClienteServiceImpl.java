package com.banco.service.impl;

import com.banco.dto.ClienteDTO;
import com.banco.entity.Cliente;
import com.banco.repository.ClienteRepository;
import com.banco.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    public ResponseEntity<ClienteDTO> create(ClienteDTO cliente) {
        Cliente cliente1 = new Cliente();
        cliente1.setNombre(cliente.getNombre());
        cliente1.setApellidos(cliente.getApellidos());
        cliente1.setDireccion(cliente.getDireccion());
        clienteRepository.save(cliente1);
        ClienteDTO dto = modelMapper.map(cliente1, ClienteDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<ClienteDTO> update(Integer id, ClienteDTO cliente) {
        Cliente cliente1 = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("El cliente no existe"));
        cliente1.setNombre(cliente.getNombre());
        cliente1.setApellidos(cliente.getApellidos());
        cliente1.setDireccion(cliente.getDireccion());
        clienteRepository.save(cliente1);
        ClienteDTO dto = modelMapper.map(cliente1, ClienteDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Cliente> delete(Integer id) {
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
