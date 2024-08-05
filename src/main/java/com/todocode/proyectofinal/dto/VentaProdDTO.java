/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.todocode.proyectofinal.dto;

import com.todocode.proyectofinal.model.Cliente;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaProdDTO {
    private Long id_venta;
    private LocalDate fecha_venta;
    private Double total;
    private List<ProductoVenDTO> listaProd;
    private ClienteDTO cliente;

    public VentaProdDTO() {
    }

    public VentaProdDTO(Long id_venta, LocalDate fecha_venta, Double total, List<ProductoVenDTO> listaProd, ClienteDTO cliente) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.listaProd = listaProd;
        this.cliente = cliente;
    }
    
    
}
