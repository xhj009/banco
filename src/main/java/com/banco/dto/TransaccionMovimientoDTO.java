package com.banco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransaccionMovimientoDTO {
    private Integer id;
    @NotEmpty(message = "cuentaOrigen no puede estar vacio")
    private String cuentaOrigen;
    @Min(0)
    private Double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private String usuario;
    private String tipo;
}
