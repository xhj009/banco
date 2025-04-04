package com.banco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CuentaDTO {
    private Integer id;
    private String numeroCuenta;
    private double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private Integer cliente_id;
}
