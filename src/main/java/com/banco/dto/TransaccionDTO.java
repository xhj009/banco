package com.banco.dto;

import com.banco.entity.Cuenta;
import com.banco.entity.Transaccion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

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
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//    private LocalDateTime fecha;
    private OffsetDateTime fecha;
    private String usuario;
    private String tipo;

//    public TransaccionDTO(Transaccion t) {
//        this.id = t.getId();
//        this.cuentaDestino = t.getCuentaDestino();
//        this.cantidad = t.getCantidad();
//        this.usuario = t.getUsuario();
//        this.fecha = t.getFecha();
//
//        // Convertir List<Cuenta> a String
//        this.cuentaOrigen = t.getCuentaOrigen()
//                .stream()
//                .map(Cuenta::getNumeroCuenta)
//                .collect(Collectors.joining(", "));
//    }


}
