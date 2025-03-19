package com.banco.service;

import com.banco.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();
    
    ResponseEntity<Cliente> findById(@PathVariable Integer id);

    ResponseEntity<Cliente> create(@RequestBody Cliente cliente);

    ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody Cliente cliente);

    ResponseEntity<Cliente> delete(@PathVariable Integer id);
}
