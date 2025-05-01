package com.banco.controller;

import com.banco.dto.CuentaDTO;
import com.banco.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cuenta")
//@CrossOrigin(origins = "http://localhost:4200")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("")
    public List<CuentaDTO> getAll(){
       return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> findById(@PathVariable Integer id){
        return cuentaService.findById(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<CuentaDTO> save(@Valid @RequestBody CuentaDTO cuenta){
        return cuentaService.save(cuenta);
    }
//
//    @PutMapping("/depositar/{id}")
//    public ResponseEntity<CuentaDTO> depositar(@PathVariable Integer id,@Valid @RequestBody CuentaDTO cuenta){
//        return cuentaService.deposito(id,cuenta.getCantidad());
//    }

//    @PutMapping("/retirar/{id}")
//    public ResponseEntity<CuentaDTO> retirar(@PathVariable Integer id,@Valid @RequestBody CuentaDTO cuenta){
//        return cuentaService.retirar(id,cuenta.getCantidad());
//    }

//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<CuentaDTO> delete(@PathVariable Integer id){
//        return cuentaService.delete(id);
//    }
}
