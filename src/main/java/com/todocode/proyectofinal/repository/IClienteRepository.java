 
package com.todocode.proyectofinal.repository;

import com.todocode.proyectofinal.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
    boolean existsByDni(String dni);
}
