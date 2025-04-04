package com.banco.service.impl;

import com.banco.dto.CuentaDTO;
import com.banco.dto.TransaccionDTO;
import com.banco.entity.Cuenta;
import com.banco.entity.Mensaje;
import com.banco.entity.Transaccion;
import com.banco.repository.CuentaRepository;
import com.banco.repository.TransaccionRepository;
import com.banco.service.TransaccionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImpl implements TransaccionService {
    @Autowired
    TransaccionRepository transaccionRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    @Override
    public ResponseEntity<Transaccion> findById(Integer id) {
       Transaccion transaccion = transaccionRepository.findById(id).get();
        return ResponseEntity.ok(transaccion);
    }

    @Override
    public ResponseEntity<TransaccionDTO> create(TransaccionDTO transaccion) {
        Transaccion transaccion1 = new Transaccion();
        List<Cuenta> transaccions = new ArrayList<>();

        Cuenta cuentaOrigen =  cuentaRepository.findBynumeroCuenta(transaccion.getCuentaOrigen());

       if (cuentaOrigen == null){
           return new ResponseEntity(new Mensaje("La cuenta origen no existe"),HttpStatus.NOT_FOUND);
       }

        Double descontar = cuentaOrigen.getCantidad() - transaccion.getCantidad();
        cuentaOrigen.setCantidad(descontar);

        System.out.println(cuentaOrigen.getCantidad() < 0 );

        if (cuentaOrigen.getCantidad() < 0 || cuentaOrigen.getCantidad() < descontar ){
            //System.out.println("dinero insuficiente");
            return new ResponseEntity(new Mensaje("Dinero insuficiente"),HttpStatus.NOT_FOUND);
        } else {
            cuentaRepository.save(cuentaOrigen);
            transaccions.add(cuentaOrigen);
            transaccion1.setCuentaOrigen(transaccions);

            transaccion1.setCantidad(transaccion.getCantidad());

            Cuenta cuentaDestino = cuentaRepository.findBynumeroCuenta(transaccion.getCuentaDestino());

            if (cuentaDestino == null){
                return new ResponseEntity(new Mensaje("La cuenta destino no existe"),HttpStatus.NOT_FOUND);
            }
            Double aumentar = cuentaDestino.getCantidad() + transaccion.getCantidad();
            cuentaDestino.setCantidad(aumentar);
            cuentaRepository.save(cuentaDestino);

            transaccion1.setCuentaDestino(transaccion.getCuentaDestino());
            LocalDateTime fecha = LocalDateTime.now();
            transaccion1.setFecha(fecha);
            transaccionRepository.save(transaccion1);
        }
        TransaccionDTO dto = modelMapper.map(transaccion1, TransaccionDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Transaccion> update(Integer id, Transaccion transaccion) {
        return null;
    }

    @Override
    public ResponseEntity<Transaccion> delete(Integer id) {
        return null;
    }
}
