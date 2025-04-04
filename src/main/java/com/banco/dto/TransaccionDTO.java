package com.banco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDTO {
    private Integer id;
    @NotEmpty(message = "cuentaOrigen no puede estar vacio")
    private String cuentaOrigen;
    @NotEmpty(message = "cuentaDestino no puede estar vacio")
    private String cuentaDestino;
    @Min(0)
    private Double cantidad;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
}
