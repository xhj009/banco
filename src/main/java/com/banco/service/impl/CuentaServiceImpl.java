package com.banco.service.impl;

import com.banco.config.ModelMapperConfig;
import com.banco.dto.CuentaDTO;
import com.banco.entity.Cliente;
import com.banco.entity.Cuenta;
import com.banco.repository.ClienteRepository;
import com.banco.repository.CuentaRepository;
import com.banco.service.CuentaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<CuentaDTO> findAll() {
//      List<Cuenta> cuenta = cuentaRepository.findAll();
//      List<CuentaDTO> dto = (List<CuentaDTO>) cuenta.stream().map(cuenta1 -> modelMapper.map(cuenta1, CuentaDTO.class));
//        return dto;
        return cuentaRepository.findAll()
                .stream()
                .map((cuenta) -> modelMapper.map(cuenta, CuentaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CuentaDTO> findById(Integer id) {
      Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
      CuentaDTO dto = modelMapper.map(cuenta, CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDTO> save(CuentaDTO cuenta) {
        Cuenta cuent = new Cuenta();
        if (cuentaRepository.existsBynumeroCuenta(cuenta.getNumeroCuenta())){
            return ResponseEntity.noContent().build();
        }
        cuent.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuent.setCantidad(0.00);
        LocalDateTime fecha = LocalDateTime.now();
        cuent.setFechaCreacion(fecha);

        Cliente cliente = clienteRepository.findById((cuenta.getCliente_id())).get();
        cuent.setCliente(cliente);
        cuentaRepository.save(cuent);
        CuentaDTO dto = modelMapper.map(cuent, CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDTO> deposito(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
        double total = cuenta1.getCantidad() + cantidad;
        cuenta1.setCantidad(total);
        cuentaRepository.save(cuenta1);
        CuentaDTO dto = modelMapper.map(cuenta1, CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }
    //Cliente cliente = clienteRepository.searchById((cuenta.getCliente_id()));
    @Override
    public ResponseEntity<CuentaDTO> retirar(Integer id, Double cantidad) {
        Cuenta cuenta1 = cuentaRepository.findById(id).get();
        cuenta1.setCantidad(cuenta1.getCantidad() - cantidad);
        cuentaRepository.save(cuenta1);
        CuentaDTO dto = modelMapper.map(cuenta1, CuentaDTO.class);
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
    public ResponseEntity<CuentaDTO> delete(Integer id) {
        cuentaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
