package com.todocode.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    private Long id_product;

    private String nombrePro;

    private String marca;

    private Double costo;

    private int stock;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id_product, String nombrePro, String marca, Double costo, int stock) {
        this.id_product = id_product;
        this.nombrePro = nombrePro;
        this.marca = marca;
        this.costo = costo;
        this.stock = stock;
    }
    
    
}
