package com.banco.service.impl;

import com.banco.config.ObtenerUsuario;
import com.banco.dto.CuentaDTO;
import com.banco.dto.TransaccionDTO;
import com.banco.dto.TransaccionMovimientoDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional

public class TransaccionServiceImpl implements TransaccionService {
    @Autowired
    TransaccionRepository transaccionRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObtenerUsuario obtenerUsuario;


    @Override
    public List<TransaccionDTO> findAll() {
        //return transaccionRepository.find All();
        return transaccionRepository.findAllByUsuario(obtenerUsuario.usuario())
                .stream()
                .map(transaccion -> modelMapper.map(transaccion,TransaccionDTO.class))
                //.map(TransaccionDTO::new)
                .toList();

    }

    @Override
    public ResponseEntity<TransaccionDTO> findById(Integer id) {
       //Transaccion transaccion = transaccionRepository.findById(id).get();
        Transaccion transaccion = transaccionRepository.findByUsuarioAndId(obtenerUsuario.usuario(),id);
        boolean existe = transaccionRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe) {
            return new ResponseEntity(new Mensaje("La transaccion no existe por el usuario"),HttpStatus.NOT_FOUND);
        }
        TransaccionDTO dto = modelMapper.map(transaccion,TransaccionDTO.class);
        //return ResponseEntity.ok(dto);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TransaccionDTO> create(TransaccionDTO transaccionDTO) {
        // Paso 1: Buscar las cuentas de origen y destino
        Cuenta cuentaOrigen = cuentaRepository.findBynumeroCuenta(transaccionDTO.getCuentaOrigen());
        if (cuentaOrigen == null) {
            //return ResponseEntity.badRequest().body("Cuenta origen no encontrada.");
            return new ResponseEntity(new Mensaje("Cuenta origen no encontrada"),HttpStatus.NOT_FOUND);
        }

        Cuenta cuentaDestino = cuentaRepository.findBynumeroCuenta(transaccionDTO.getCuentaDestino());
        if (cuentaDestino == null) {
            //return ResponseEntity.badRequest().body("Cuenta destino no encontrada.");
            return new ResponseEntity(new Mensaje("Cuenta destino no encontrada"),HttpStatus.NOT_FOUND);
        }

        // Paso 2: Validar que haya suficiente saldo en la cuenta origen
        if (cuentaOrigen.getCantidad() < transaccionDTO.getCantidad()) {
            //return ResponseEntity.badRequest().body("Saldo insuficiente en la cuenta origen.");
            return new ResponseEntity(new Mensaje("Saldo insuficiente"),HttpStatus.NOT_FOUND);
        }

        // Paso 3: Crear la transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino.getNumeroCuenta());
        transaccion.setCantidad(transaccionDTO.getCantidad()); // Establecer la cantidad de la transacción
        transaccion.setUsuario(obtenerUsuario.usuario()); // Usuario actual
       //transaccion.setFecha(LocalDateTime.now()); // Fecha de la transacción
        transaccion.setFecha(OffsetDateTime.now());
        transaccion.setTipo("Transaccion");

        // Paso 4: Actualizar las cuentas
        // Descontar de la cuenta origen
        Double saldoOrigen = cuentaOrigen.getCantidad() - transaccionDTO.getCantidad();
        cuentaOrigen.setCantidad(saldoOrigen);

        // Aumentar en la cuenta destino
        Double saldoDestino = cuentaDestino.getCantidad() + transaccionDTO.getCantidad();
        cuentaDestino.setCantidad(saldoDestino);

        // Guardar las cuentas actualizadas
        cuentaRepository.save(cuentaOrigen);
        cuentaRepository.save(cuentaDestino);

        // Paso 5: Guardar la transacción en el repositorio
        transaccionRepository.save(transaccion);

        // Paso 6: Convertir la entidad Transaccion a DTO para devolver al cliente
        TransaccionDTO dto = modelMapper.map(transaccion, TransaccionDTO.class);

        return ResponseEntity.ok(dto); // Retornar la transacción creada como respuesta
    }

    @Override
    public ResponseEntity<TransaccionDTO> ingreso(Integer id, Double cantidad) {

        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe){
            return new ResponseEntity(new Mensaje("La cuenta no existe"),HttpStatus.NOT_FOUND);
        }

        Cuenta cuenta = cuentaRepository.findById(id).get();
        cuenta.setCantidad(cuenta.getCantidad() + cantidad);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("Ingreso");
        transaccion.setCantidad(cantidad);
        transaccion.setFecha(OffsetDateTime.now());
        transaccion.setCuentaOrigen(cuenta);
        transaccion.setUsuario(obtenerUsuario.usuario());
        transaccionRepository.save(transaccion);

        TransaccionDTO dto = modelMapper.map(transaccion,TransaccionDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TransaccionDTO> retirar(Integer id, Double cantidad) {

        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe){
            return new ResponseEntity(new Mensaje("La cuenta no existe"),HttpStatus.NOT_FOUND);
        }

        Cuenta cuenta = cuentaRepository.findById(id).get();
        cuenta.setCantidad(cuenta.getCantidad() - cantidad);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("Retirar");
        transaccion.setCantidad(cantidad);
        transaccion.setFecha(OffsetDateTime.now());
        transaccion.setCuentaOrigen(cuenta);
        transaccion.setUsuario(obtenerUsuario.usuario());
        transaccionRepository.save(transaccion);

        TransaccionDTO dto = modelMapper.map(transaccion,TransaccionDTO.class);
        return ResponseEntity.ok(dto);
    }


//    public ResponseEntity<TransaccionDTO> create(TransaccionDTO transaccion) {
//        Transaccion transaccion1 = new Transaccion();
//        Cuenta cuentaOrigen = cuentaRepository.findBynumeroCuenta(transaccion.getCuentaOrigen());
//        transaccion1.setCuentaOrigen(cuentaOrigen);
//
//        Double descontar = cuentaOrigen.getCantidad() - transaccion.getCantidad();
//        cuentaOrigen.setCantidad(descontar);
//
//        Cuenta cuentaDestino = cuentaRepository.findBynumeroCuenta(transaccion.getCuentaDestino());
//
//        transaccion1.setCantidad(transaccion1.getCantidad());
//
//        Double aumentar = cuentaDestino.getCantidad() + transaccion.getCantidad();
//        cuentaDestino.setCantidad(aumentar);
//        cuentaRepository.save(cuentaDestino);
//        transaccion1.setCuentaDestino(transaccion.getCuentaDestino());
//
//
//        transaccion1.setUsuario(obtenerUsuario.usuario());
//        LocalDateTime fecha = LocalDateTime.now();
//        transaccion1.setFecha(fecha);
//        transaccionRepository.save(transaccion1);
//
//        TransaccionDTO dto = modelMapper.map(transaccion1, TransaccionDTO.class);
//        return ResponseEntity.ok(dto);
//    }

    // este es el bueno
   // @Override
//    public ResponseEntity<TransaccionDTO> create(TransaccionDTO transaccion) {
//        Transaccion transaccion1 = new Transaccion();
//        List<Cuenta> transaccions = new ArrayList<>();
//
//        Cuenta cuentaOrigen =  cuentaRepository.findBynumeroCuenta(transaccion.getCuentaOrigen());
//
//       if (cuentaOrigen == null){
//           return new ResponseEntity(new Mensaje("La cuenta origen no existe"),HttpStatus.NOT_FOUND);
//       }
//
//        Double descontar = cuentaOrigen.getCantidad() - transaccion.getCantidad();
//        cuentaOrigen.setCantidad(descontar);
//
//        System.out.println(cuentaOrigen.getCantidad() < 0 );
//
//        if (cuentaOrigen.getCantidad() < 0 || cuentaOrigen.getCantidad() < descontar ){
//            //System.out.println("dinero insuficiente");
//            return new ResponseEntity(new Mensaje("Dinero insuficiente"),HttpStatus.NOT_FOUND);
//        } else {
//            cuentaRepository.save(cuentaOrigen);
//            transaccions.add(cuentaOrigen);
//            transaccion1.setCuentaOrigen(transaccions);
//
//            transaccion1.setCantidad(transaccion.getCantidad());
//            Cuenta cuentaDestino = cuentaRepository.findBynumeroCuenta(transaccion.getCuentaDestino());
//
//            if (cuentaDestino == null){
//                return new ResponseEntity(new Mensaje("La cuenta destino no existe"),HttpStatus.NOT_FOUND);
//            }
//            Double aumentar = cuentaDestino.getCantidad() + transaccion.getCantidad();
//            cuentaDestino.setCantidad(aumentar);
//            cuentaRepository.save(cuentaDestino);
//
//            transaccion1.setCuentaDestino(transaccion.getCuentaDestino());
//            LocalDateTime fecha = LocalDateTime.now();
//            transaccion1.setFecha(fecha);
//            transaccion1.setUsuario(obtenerUsuario.usuario());
//            transaccionRepository.save(transaccion1);
//        }
//        TransaccionDTO dto = modelMapper.map(transaccion1, TransaccionDTO.class);
//        return ResponseEntity.ok(dto);
//    }

    @Override
    public ResponseEntity<Transaccion> update(Integer id, Transaccion transaccion) {
        return null;
    }

    @Override
    public ResponseEntity<Transaccion> delete(Integer id) {
        return null;
    }
}
