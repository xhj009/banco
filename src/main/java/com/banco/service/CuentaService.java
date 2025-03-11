package com.banco.service;

import com.banco.entity.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public interface CuentaService {

    List<Cuenta> findAll();

    ResponseEntity<Cuenta> findById(@PathVariable Integer id);

    ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta);

    ResponseEntity<Cuenta> deposito(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<Cuenta> retirar(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<Cuenta> update(@PathVariable Integer id ,@RequestBody Cuenta cuenta);

    ResponseEntity<Cuenta> delete(@PathVariable Integer id);
}
