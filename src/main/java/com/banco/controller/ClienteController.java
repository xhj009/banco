package com.banco.controller;

import com.banco.dto.ClienteDTO;
import com.banco.entity.Cliente;
import com.banco.service.ClienteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    public List<Cliente> getAll() {
       return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id){
        return clienteService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO cliente){
        return clienteService.create(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update (@PathVariable Integer id, @RequestBody ClienteDTO cliente){
        return clienteService.update(id,cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete (@PathVariable Integer id){
        return clienteService.delete(id);
    }
}
