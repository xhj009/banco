package com.banco.service;

import com.banco.dto.CuentaDto;
import com.banco.entity.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public interface CuentaService {

    List<CuentaDto> findAll();

    ResponseEntity<CuentaDto> findById(@PathVariable Integer id);

    ResponseEntity<CuentaDto> save(@RequestBody CuentaDto cuenta);

    ResponseEntity<CuentaDto> deposito(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<CuentaDto> retirar(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<Cuenta> update(@PathVariable Integer id ,@RequestBody Cuenta cuenta);

    ResponseEntity<CuentaDto> delete(@PathVariable Integer id);
}
