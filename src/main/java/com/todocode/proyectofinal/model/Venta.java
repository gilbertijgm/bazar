
package com.todocode.proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;
    private LocalDate fecha_venta;
    private Double total;
    @ManyToMany
    @JoinTable(name = "producto_venta",
            joinColumns = @JoinColumn(name = "id_venta", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_product", nullable = false))
    private List<Producto> listaProd;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonBackReference
    private Cliente cliente;

    public Venta() {
    }
    

    public Venta(Long id_venta, LocalDate fecha_venta, Double total, List<Producto> listaProd, Cliente cliente) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.listaProd = listaProd;
        this.cliente = cliente;
    }

 

    
    
    
}
