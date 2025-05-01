package com.banco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@ManyToMany(targetEntity = Cuenta.class,fetch = FetchType.EAGER)
    @ManyToOne(targetEntity = Cuenta.class)
    //@JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;
    //private List<Cuenta> cuentaOrigen;
    private String cuentaDestino;
    private Double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime fecha;
    private String usuario;
    private String tipo;
}
