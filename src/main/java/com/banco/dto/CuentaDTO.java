package com.banco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CuentaDTO {
    private Integer id;
    private String numeroCuenta;
    @Min(0)
    private double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private String usuario;
    //private Integer cliente_id;

}
