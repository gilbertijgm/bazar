package com.todocode.proyectofinal.service;

import com.todocode.proyectofinal.dto.ProductoDTO;
import com.todocode.proyectofinal.dto.ProductoVenDTO;
import com.todocode.proyectofinal.exception.DuplicateResourceException;
import com.todocode.proyectofinal.exception.ResourceNotFoundException;
import com.todocode.proyectofinal.model.Producto;
import com.todocode.proyectofinal.repository.IProductoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository proRepo;

    @Override
    public Producto createProd(Producto producto) {
        //verificar si existe un producto con el mismo nombre
        if (proRepo.existsByNombrePro(producto.getNombrePro())) {
            throw new DuplicateResourceException("Ya existe producto con ese nombre");
        }

        Producto prod = proRepo.save(producto);

        return prod;
    }

    @Override
    public List<ProductoDTO> getAllPro() {
        List<Producto> listaProd = proRepo.findAll();

        if (listaProd.isEmpty()) {
            // Manejar el caso de lista vacía si es necesario
            // Puedes devolver una lista vacía en lugar de lanzar una excepción
            // return new ArrayList<>();   
            throw new ResourceNotFoundException("No se encontraron productos.");
        }
        List<ProductoDTO> proDTO = new ArrayList();
        for (Producto producto : listaProd) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId_product(producto.getId_product());
            dto.setNombrePro(producto.getNombrePro());
            dto.setCosto(producto.getCosto());
            dto.setMarca(producto.getMarca());
            dto.setStock(producto.getStock());
            proDTO.add(dto);
        }

        return proDTO;
    }

    @Override
    public ProductoDTO getByIdPro(Long id) {
        //verificar sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        
        //busca el producto por id si no lo encuentra retorna un msje
        Producto prod = proRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        
        ProductoDTO proDTO = new ProductoDTO();
        proDTO.setId_product(prod.getId_product());
        proDTO.setNombrePro(prod.getNombrePro());
        proDTO.setCosto(prod.getCosto());
        proDTO.setMarca(prod.getMarca());
        proDTO.setStock(prod.getStock());
        
        return proDTO;
    }

    @Override
    public Producto editPro(Long id, Producto producto) {
        //verificar sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        
        //busca el producto por id si no lo encuentra retorna un msje
        Producto prodEdit = proRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        
        // Validar que el DNI sea único, excluyendo el cliente actual
        if (producto.getNombrePro() != null && !producto.getNombrePro().equals(prodEdit.getNombrePro())
                && proRepo.existsByNombrePro(producto.getNombrePro())) {

            throw new DuplicateResourceException("El nombre de producto ya está registrado.");
        }
        
        //actualizamos los campos existente
        prodEdit.setNombrePro(producto.getNombrePro());
        prodEdit.setMarca(producto.getMarca());
        prodEdit.setCosto(producto.getCosto());
        prodEdit.setStock(producto.getStock());
        
        proRepo.save(prodEdit);
        
        return prodEdit;
    }

    @Override
    public void deletePro(Long id) {
        //verificar sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        
        //verificamos si existe el cliente con ese id
        if (!proRepo.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con el ID: " + id);
        }
        
        proRepo.deleteById(id);
    }

    @Override
    public List<ProductoDTO> bajoStock() {
        List<Producto> listaProd = proRepo.findAll();
        
        List<ProductoDTO> bajoStock = new ArrayList<>();
        for (Producto producto : listaProd) {
            if (producto.getStock() < 5) {
                ProductoDTO dto = new ProductoDTO();
                dto.setId_product(producto.getId_product());
                dto.setNombrePro(producto.getNombrePro());
                dto.setCosto(producto.getCosto());
                dto.setMarca(producto.getMarca());
                dto.setStock(producto.getStock());
                bajoStock.add(dto);
            }else{
                throw new ResourceNotFoundException("No hay productos con falta de stock 5");
            }
        }
        
        return bajoStock;
    }

}
