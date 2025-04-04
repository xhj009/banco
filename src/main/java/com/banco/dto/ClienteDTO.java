package com.banco.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String direccion;
}
