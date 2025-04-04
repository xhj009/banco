package com.banco.service;

import com.banco.dto.CuentaDTO;
import com.banco.entity.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface CuentaService {

    List<CuentaDTO> findAll();

    ResponseEntity<CuentaDTO> findById(@PathVariable Integer id);

    ResponseEntity<CuentaDTO> save(@RequestBody CuentaDTO cuenta);

    ResponseEntity<CuentaDTO> deposito(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<CuentaDTO> retirar(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<Cuenta> update(@PathVariable Integer id ,@RequestBody Cuenta cuenta);

    ResponseEntity<CuentaDTO> delete(@PathVariable Integer id);
}
