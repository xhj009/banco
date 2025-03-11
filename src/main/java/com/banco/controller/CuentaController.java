package com.banco.controller;

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
    CuentaService cuentaService;

    @GetMapping("")
    public List<Cuenta> getAll(){
       return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Integer id){
        return cuentaService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta){
        return cuentaService.save(cuenta);
    }

    @PutMapping("/depositar/{id}")
    public ResponseEntity<Cuenta> depositar(@PathVariable Integer id, @RequestBody Cuenta cuenta){
        return cuentaService.deposito(id,cuenta.getCantidad());
    }

    @PutMapping("/retirar/{id}")
    public ResponseEntity<Cuenta> retirar(@PathVariable Integer id,@RequestBody Cuenta cuenta){
        return cuentaService.retirar(id,cuenta.getCantidad());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Cuenta> delete(@PathVariable Integer id){
        return cuentaService.delete(id);
    }
}
