    
package com.todocode.proyectofinal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;
    @NotBlank(message = "El campo DNI no puede estar vacio")
    @Size(min = 8, max = 10, message = "El DNI debe tener entre 8 y 10 caracteres")
    @Pattern(regexp = "\\d{8,10}", message = "El DNI debe contener solo dígitos numericos")
    private String dni;
    @NotBlank(message = "El campo nombre no puede estar vacio")
    @Size(min = 2, max = 50, message = "El campo nombre debe tener minimo 2 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El campo nombre debe contener solo letras y espacios")
    private String nomApe;
    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Venta> listaVenta;

    public Cliente() {
    }

    public Cliente(Long id_cliente, String dni, String nomApe, List<Venta> listaVenta) {
        this.id_cliente = id_cliente;
        this.dni = dni;
        this.nomApe = nomApe;
        this.listaVenta = listaVenta;
    }

    
}
