package com.todocode.proyectofinal.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;
    @NotBlank(message = "El campo nombre no puede estar vacio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    private String nombrePro;
    @NotBlank(message = "El campo marca no puede estar vacio")
    @Size(min = 3, max = 60, message = "La marca debe tener entre 2 y 60 caracteres")
    private String marca;
    @NotNull(message = "El costo no puede ser nulo o vacio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a cero")
    private Double costo;
    @Min(value = 2, message = "El stock no puede ser negativo")
    private int stock;
    
    @ManyToMany(mappedBy = "listaProd")
    private List<Venta> listaVenta;
    

    public Producto() {
    }

    public Producto(Long id_product, String nombrePro, String marca, Double costo, int stock, List<Venta> listaVenta) {
        this.id_product = id_product;
        this.nombrePro = nombrePro;
        this.marca = marca;
        this.costo = costo;
        this.stock = stock;
        this.listaVenta = listaVenta;
    }

    
    
    
    

}
