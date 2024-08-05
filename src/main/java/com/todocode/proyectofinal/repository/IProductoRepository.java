package com.todocode.proyectofinal.repository;


import com.todocode.proyectofinal.dto.ProductoVenDTO;
import com.todocode.proyectofinal.model.Producto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombrePro(String nombrePro);

    @Query("SELECT p.costo FROM Producto p WHERE p.id_product = :idProduct")
    Double findCostoById(Long idProduct);
    
    @Query("SELECT new com.todocode.proyectofinal.dto.ProductoVenDTO(p.id_product, p.nombrePro, p.costo) FROM Producto p WHERE p.id_product = :idProduct")
    Optional<ProductoVenDTO> findProductoDTOById(@Param("idProduct") Long idProduct);

}
