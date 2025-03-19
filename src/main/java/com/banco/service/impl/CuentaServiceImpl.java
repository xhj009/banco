package com.banco.service.impl;

import com.banco.config.ModelMapperConfig;
import com.banco.dto.CuentaDto;
import com.banco.entity.Cliente;
import com.banco.entity.Cuenta;
import com.banco.repository.ClienteRepository;
import com.banco.repository.CuentaRepository;
import com.banco.service.CuentaService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ModelMapperConfig modelMapperConfig;

    @Override
    public List<CuentaDto> findAll() {
//      List<Cuenta> cuenta = cuentaRepository.findAll();
//      List<CuentaDto> dto = (List<CuentaDto>) cuenta.stream().map(cuenta1 -> modelMapper.map(cuenta1, CuentaDto.class));
//        return dto;
        return cuentaRepository.findAll()
                .stream()
                .map((cuenta) -> modelMapper.map(cuenta,CuentaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CuentaDto> findById(Integer id) {
      Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
      CuentaDto dto = modelMapper.map(cuenta, CuentaDto.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDto> save(CuentaDto cuenta) {
        Cuenta cuent = new Cuenta();
        cuent.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuent.setCantidad(0.00);
        LocalDateTime fecha = LocalDateTime.now();
        cuent.setFechaCreacion(fecha);

        Cliente cliente = clienteRepository.findById((cuenta.getCliente_id())).get();
        cuent.setCliente(cliente);
        cuentaRepository.save(cuent);
        CuentaDto dto = modelMapper.map(cuent,CuentaDto.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDto> deposito(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
        double total = cuenta1.getCantidad() + cantidad;
        cuenta1.setCantidad(total);
        cuentaRepository.save(cuenta1);
        CuentaDto dto = modelMapper.map(cuenta1, CuentaDto.class);
        return ResponseEntity.ok(dto);
    }
    //Cliente cliente = clienteRepository.searchById((cuenta.getCliente_id()));
    @Override
    public ResponseEntity<CuentaDto> retirar(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).get();
        cuenta1.setCantidad(cuenta1.getCantidad() - cantidad);
        cuentaRepository.save(cuenta1);
        CuentaDto dto = modelMapper.map(cuenta1, CuentaDto.class);
        return ResponseEntity.ok(dto);
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
    public ResponseEntity<CuentaDto> delete(Integer id) {
        cuentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
