package com.banco.service;

//import com.banco.dto.ClienteDTO;
import com.banco.dto.TransaccionDTO;
//import com.banco.entity.Cliente;
import com.banco.dto.TransaccionMovimientoDTO;
import com.banco.entity.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface TransaccionService {
    List<TransaccionDTO> findAll();

    ResponseEntity<TransaccionDTO> findById(@PathVariable Integer id);

    ResponseEntity<TransaccionDTO> create(@RequestBody TransaccionDTO transaccion);

    ResponseEntity<TransaccionMovimientoDTO> ingreso(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<TransaccionMovimientoDTO> retirar(@PathVariable Integer id, @RequestBody Double cantidad);

    ResponseEntity<Transaccion> update(@PathVariable Integer id, @RequestBody Transaccion transaccion);

    ResponseEntity<Transaccion> delete(@PathVariable Integer id);

}
