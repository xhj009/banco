package com.banco.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotEmpty(message = "Los apellidos no pueden estar vacios")
    private String apellidos;
    @NotEmpty(message = "La direccion no puede estar vacia")
    private String direccion;
}
