package com.todocode.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteDTO {
    private Long id_cliente;
    private String dni;
    private String nomApe;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id_cliente, String dni, String nomApe) {
        this.id_cliente = id_cliente;
        this.dni = dni;
        this.nomApe = nomApe;
    }
    
    
}
