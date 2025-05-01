package com.banco.controller;

import com.banco.dto.CuentaDTO;
import com.banco.dto.TransaccionDTO;
import com.banco.dto.TransaccionMovimientoDTO;
import com.banco.entity.Transaccion;
import com.banco.service.TransaccionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transaccion")
@CrossOrigin(origins = "http://localhost:4200")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("")
    public List<TransaccionDTO> findAll(){
        return transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> findById(@PathVariable Integer id){
        return transaccionService.findById(id);
    }
    @PostMapping("/crear")
    public ResponseEntity<TransaccionDTO> create(@Valid @RequestBody TransaccionDTO transaccion){
        return transaccionService.create(transaccion);
    }

    @PutMapping("ingresar/{id}")
    public ResponseEntity<TransaccionDTO> ingreso(@PathVariable Integer id, @RequestBody CuentaDTO cuenta){
        return transaccionService.ingreso(id, cuenta.getCantidad());
    }

    @PutMapping("retirar/{id}")
    public ResponseEntity<TransaccionDTO> retirar(@PathVariable Integer id, @RequestBody CuentaDTO cuenta){
        return transaccionService.retirar(id, cuenta.getCantidad());
    }

}

