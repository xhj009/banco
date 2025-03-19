package com.banco.controller;

import com.banco.dto.CuentaDto;
import com.banco.entity.Cuenta;
import com.banco.service.CuentaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("")
    public List<CuentaDto> getAll(){
       return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDto> findById(@PathVariable Integer id){
        return cuentaService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<CuentaDto> save(@RequestBody CuentaDto cuenta){
        return cuentaService.save(cuenta);
    }

    @PutMapping("/depositar/{id}")
    public ResponseEntity<CuentaDto> depositar(@PathVariable Integer id, @RequestBody CuentaDto cuenta){
        return cuentaService.deposito(id,cuenta.getCantidad());
    }

    @PutMapping("/retirar/{id}")
    public ResponseEntity<CuentaDto> retirar(@PathVariable Integer id,@RequestBody CuentaDto cuenta){
        return cuentaService.retirar(id,cuenta.getCantidad());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<CuentaDto> delete(@PathVariable Integer id){
        return cuentaService.delete(id);
    }
}
