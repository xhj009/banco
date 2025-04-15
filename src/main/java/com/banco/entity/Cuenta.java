package com.banco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numeroCuenta;
    private double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
//    @ManyToOne(targetEntity = Cliente.class)
//    private Cliente cliente;
    //@ManyToMany(targetEntity = Transaccion.class)
    //cascade = CascadeType.ALL,
    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.EAGER)
    private List<Transaccion> transacciones;
    private String usuario;
}
