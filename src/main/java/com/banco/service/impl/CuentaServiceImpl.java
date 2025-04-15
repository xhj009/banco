package com.banco.service.impl;

import com.banco.config.ObtenerUsuario;
import com.banco.dto.CuentaDTO;
//import com.banco.entity.Cliente;
import com.banco.entity.Cuenta;
import com.banco.entity.Mensaje;
//import com.banco.repository.ClienteRepository;
import com.banco.repository.CuentaRepository;
import com.banco.service.CuentaService;
import com.banco.service.TransaccionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;
//    @Autowired
//    private ClienteRepository clienteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObtenerUsuario obtenerUsuario;

    @Override
    public List<CuentaDTO> findAll() {
//        return cuentaRepository.findAll()
//                .stream()
//                .map((cuenta) -> modelMapper.map(cuenta, CuentaDTO.class))
//                .collect(Collectors.toList());
        return cuentaRepository.findAllByUsuario(obtenerUsuario.usuario())
                .stream()
                .map(cuenta -> modelMapper.map(cuenta, CuentaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<CuentaDTO> findById(Integer id) {
//      Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
//      CuentaDTO dto = modelMapper.map(cuenta, CuentaDTO.class);
//        return ResponseEntity.ok(dto);

        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe){
            return new ResponseEntity(new Mensaje("La cuenta no existe por el usuario"),HttpStatus.NOT_FOUND);
        }

        Cuenta cuenta = cuentaRepository.findByUsuarioAndId(obtenerUsuario.usuario(),id);
        CuentaDTO dto = modelMapper.map(cuenta,CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDTO> save(CuentaDTO cuenta) {
        Cuenta cuent = new Cuenta();
        if (cuentaRepository.existsBynumeroCuenta(cuenta.getNumeroCuenta())){
            return new ResponseEntity(new Mensaje("La cuenta ya existe"), HttpStatus.NOT_FOUND);
        }
        cuent.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuent.setCantidad(0.00);
        LocalDateTime fecha = LocalDateTime.now();
        cuent.setFechaCreacion(fecha);
        cuent.setUsuario(obtenerUsuario.usuario());
//        Cliente cliente = clienteRepository.findById((cuenta.getCliente_id())).get();
//        cuent.setCliente(cliente);
        cuentaRepository.save(cuent);
        CuentaDTO dto = modelMapper.map(cuent, CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<CuentaDTO> deposito(Integer id, Double cantidad) {
        //Cuenta cuenta1 = cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("La cuenta no existe"));
        Cuenta cuenta1 = cuentaRepository.findByUsuarioAndId(obtenerUsuario.usuario(),id);
        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe) {
            return new ResponseEntity(new Mensaje("No existe la cuenta por el usuario"), HttpStatus.BAD_REQUEST);
        }
        double total = cuenta1.getCantidad() + cantidad;
        cuenta1.setCantidad(total);
        cuentaRepository.save(cuenta1);
        CuentaDTO dto = modelMapper.map(cuenta1, CuentaDTO.class);
        return ResponseEntity.ok(dto);
    }
    //Cliente cliente = clienteRepository.searchById((cuenta.getCliente_id()));
    @Override
    public ResponseEntity<CuentaDTO> retirar(Integer id, Double cantidad) {
        //Cuenta cuenta1 = cuentaRepository.findById(id).get();
        Cuenta cuenta1 = cuentaRepository.findByUsuarioAndId(obtenerUsuario.usuario(),id);
        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);
        if (!existe) {
            return new ResponseEntity(new Mensaje("No existe la cuenta por el usuario"), HttpStatus.BAD_REQUEST);
        }
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
        boolean existe = cuentaRepository.existsByUsuarioAndId(obtenerUsuario.usuario(),id);

        if (!existe){
            return new ResponseEntity(new Mensaje("La cuenta que quieres eliminar no existe"),HttpStatus.NOT_FOUND);
        }
        cuentaRepository.deleteById(id);
        return new ResponseEntity(new Mensaje("La cuenta ha sido eliminada existosamente "),HttpStatus.OK);
    }
}
