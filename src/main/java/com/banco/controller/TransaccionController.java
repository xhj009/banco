package com.banco.controller;

import com.banco.dto.TransaccionDTO;
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
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("")
    public List<Transaccion> findAll(){
        return transaccionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> findById(@PathVariable Integer id){
        return transaccionService.findById(id);
    }
    @PostMapping("/crear")
    public ResponseEntity<TransaccionDTO> create(@Valid @RequestBody TransaccionDTO transaccion){
        return transaccionService.create(transaccion);
    }
}

