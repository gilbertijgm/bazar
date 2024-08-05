 
package com.todocode.proyectofinal.service;

import com.todocode.proyectofinal.dto.ProductoDTO;
import com.todocode.proyectofinal.dto.ProductoVenDTO;
import com.todocode.proyectofinal.model.Producto;
import java.util.List;


public interface IProductoService {
    
    public Producto createProd(Producto producto);
    
    public List<ProductoDTO> getAllPro();
    
    public ProductoDTO getByIdPro(Long id);
    
    public Producto editPro(Long id, Producto producto);
    
    public void deletePro(Long id);
    
    public List<ProductoDTO> bajoStock();
}
