package com.banco.service.impl;

import com.banco.entity.Cuenta;
import com.banco.repository.CuentaRepository;
import com.banco.service.CuentaService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    CuentaRepository cuentaRepository;



    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public ResponseEntity<Cuenta> findById(Integer id) {
      Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
        return ResponseEntity.ok(cuenta);
    }

    @Override
    public ResponseEntity<Cuenta> save(Cuenta cuenta) {
        Cuenta cuent = new Cuenta();
        cuent.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuent.setCantidad(0.00);
        LocalDateTime fecha = LocalDateTime.now();
        cuent.setFechaCreacion(fecha);


        cuentaRepository.save(cuent);
        return ResponseEntity.ok(cuent);
    }

    @Override
    public ResponseEntity<Cuenta> deposito(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
        double total = cuenta1.getCantidad() + cantidad;
        cuenta1.setCantidad(total);
        cuentaRepository.save(cuenta1 );
        return ResponseEntity.ok(cuenta1);
    }

    @Override
    public ResponseEntity<Cuenta> retirar(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).get();
        cuenta1.setCantidad(cuenta1.getCantidad() - cantidad);
        cuentaRepository.save(cuenta1 );
        return ResponseEntity.ok(cuenta1);
    }


    @Override
    public ResponseEntity<Cuenta> update(Integer id, Cuenta cuenta) {
        Cuenta cuent = cuentaRepository.findById(id).get();
        cuent.setNumeroCuenta(cuenta.getNumeroCuenta());
        //cuent.setCantidad(cuenta.getCantidad());
        LocalDateTime fecha = LocalDateTime.now();
        cuent.setFechaCreacion(fecha);
        cuentaRepository.save(cuent);
        return ResponseEntity.ok(cuent);
    }

    @Override
    public ResponseEntity<Cuenta> delete(Integer id) {
        cuentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
