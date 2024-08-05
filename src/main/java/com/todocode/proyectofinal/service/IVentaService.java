
package com.todocode.proyectofinal.service;

import com.todocode.proyectofinal.dto.ResumenVentasDTO;
import com.todocode.proyectofinal.dto.VentaProdDTO;
import com.todocode.proyectofinal.model.Venta;
import java.time.LocalDate;
import java.util.List;


public interface IVentaService {
    
    public VentaProdDTO crearVenta(Venta venta);
    
    public List<VentaProdDTO> getAllVenta();
    
    public VentaProdDTO getByIdVenta(Long id);
    
    public void deleteVenta(Long id);
    
    public VentaProdDTO editVenta(Long id, Venta venta);
    
    public ResumenVentasDTO obtenerResumenVentas(LocalDate fecha);
}
