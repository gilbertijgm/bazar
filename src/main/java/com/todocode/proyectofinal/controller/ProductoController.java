    
package com.todocode.proyectofinal.controller;

import com.todocode.proyectofinal.dto.ProductoDTO;
import com.todocode.proyectofinal.dto.ProductoVenDTO;
import com.todocode.proyectofinal.model.Producto;
import com.todocode.proyectofinal.service.IProductoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductoController {
    
    @Autowired
    private IProductoService proServi;
    
    @PostMapping("/producto/crear")
    public ResponseEntity<?> guardarProducto(@Validated @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            // Recopilar todos los mensajes de error
            String errorMessage = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            // Devolver los errores en el cuerpo de la respuesta con un código de estado 400
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Producto prod = proServi.createProd(producto);

        return new ResponseEntity<>(prod, HttpStatus.CREATED);
    }
    
    @GetMapping("/producto/todos")
    public ResponseEntity<?> getProductos() {
        List<ProductoDTO> listaProd = proServi.getAllPro();
        
        if (listaProd.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(listaProd, HttpStatus.OK);
    }
    
    @GetMapping("/producto/{id_producto}")
    public ResponseEntity<?> getCliById(@PathVariable Long id_producto) {
        ProductoDTO producto = proServi.getByIdPro(id_producto);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }
    
    @PutMapping("/producto/edit/{id_producto}")
    public ResponseEntity<?> editarCliente(@PathVariable Long id_producto,
            @Validated @RequestBody Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        Producto proEdit = proServi.editPro(id_producto, producto);
        return new ResponseEntity<>(proEdit, HttpStatus.OK);
    }
    
    @DeleteMapping("/producto/delete/{id_producto}")
    public ResponseEntity<String> deleteCli(@PathVariable Long id_producto) {
        proServi.deletePro(id_producto);
        return new ResponseEntity<>("Cliente eliminado con éxito.", HttpStatus.OK);
    }

    @GetMapping("/producto/bajoStock")
    public ResponseEntity<?> bajoStock() {
        List<ProductoDTO> listaProd = proServi.bajoStock();
        
        if (listaProd.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(listaProd, HttpStatus.OK);
    }
}

