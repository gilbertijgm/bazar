 
package com.todocode.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductoVenDTO {
    private Long id_product;
    private String nombrePro;
    
    private Double costo;

    public ProductoVenDTO() {
    }

    public ProductoVenDTO(Long id_product, String nombrePro, Double costo) {
        this.id_product = id_product;
        this.nombrePro = nombrePro;
        this.costo = costo;
    }
    
    
}
