package com.todocode.proyectofinal.service;


import com.todocode.proyectofinal.dto.ClienteDTO;
import com.todocode.proyectofinal.dto.ProductoVenDTO;
import com.todocode.proyectofinal.dto.ResumenVentasDTO;
import com.todocode.proyectofinal.dto.VentaProdDTO;
import com.todocode.proyectofinal.exception.ResourceNotFoundException;
import com.todocode.proyectofinal.model.Cliente;
import com.todocode.proyectofinal.model.Producto;
import com.todocode.proyectofinal.model.Venta;
import com.todocode.proyectofinal.repository.IClienteRepository;
import com.todocode.proyectofinal.repository.IProductoRepository;
import com.todocode.proyectofinal.repository.IVentaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository venRepo;
    @Autowired
    private IProductoRepository proRepo;
    @Autowired
    private IClienteRepository cliRepo;

    

    @Override
    public VentaProdDTO crearVenta(Venta venta) {
        // Obtener la fecha actual y setearla al objeto
        LocalDate fechaActual = LocalDate.now();
        venta.setFecha_venta(fechaActual);

        // Calcular el total
        double total = 0.0;
        List<ProductoVenDTO> productoDTOs = new ArrayList<>();

        for (Producto producto : venta.getListaProd()) {
            Optional<ProductoVenDTO> optionalProductoDTO = proRepo.findProductoDTOById(producto.getId_product());
            if (optionalProductoDTO.isPresent()) {
                ProductoVenDTO productoDTO = optionalProductoDTO.get();
                total += productoDTO.getCosto();
                productoDTOs.add(productoDTO);
            } else {
                System.out.println("Producto con ID " + producto.getId_product() + " no encontrado.");
            }
        }
        venta.setTotal(total);
        Venta ven = venRepo.save(venta);

        // Crear ClienteDTO
        ClienteDTO clienteDTO;

        // Manejo del caso donde solo tienes el ID del cliente
        Optional<Cliente> optionalCliente = cliRepo.findById(venta.getCliente().getId_cliente());
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            clienteDTO = new ClienteDTO(cliente.getId_cliente(), cliente.getDni(), cliente.getNomApe());
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + venta.getCliente().getId_cliente());
        }

        // Crear una respuesta DTO con los datos necesarios
        VentaProdDTO ventaProdDTO = new VentaProdDTO();
        ventaProdDTO.setId_venta(ven.getId_venta());
        ventaProdDTO.setFecha_venta(ven.getFecha_venta());
        ventaProdDTO.setTotal(ven.getTotal());
        ventaProdDTO.setListaProd(productoDTOs);
        ventaProdDTO.setCliente(clienteDTO);

        return ventaProdDTO;
    }
    private VentaProdDTO convertirAVentaProdDTO(Venta venta) {
        // Crear ClienteDTO
        ClienteDTO clienteDTO = null;
        if (venta.getCliente() != null) {
            Cliente cliente = venta.getCliente();
            clienteDTO = new ClienteDTO(cliente.getId_cliente(), cliente.getDni(), cliente.getNomApe());
        }

        // Convertir lista de productos a ProductoDTO
        List<ProductoVenDTO> productoDTOs = new ArrayList<>();
        for (Producto producto : venta.getListaProd()) {
            ProductoVenDTO dto = new ProductoVenDTO();
            dto.setId_product(producto.getId_product());
            dto.setNombrePro(producto.getNombrePro());
            dto.setCosto(producto.getCosto());
            productoDTOs.add(dto);
        }

        // Crear y retornar VentaProdDTO
        VentaProdDTO ventaProdDTO = new VentaProdDTO();
        ventaProdDTO.setId_venta(venta.getId_venta());
        ventaProdDTO.setFecha_venta(venta.getFecha_venta());
        ventaProdDTO.setTotal(venta.getTotal());
        ventaProdDTO.setListaProd(productoDTOs);
        ventaProdDTO.setCliente(clienteDTO);
        return ventaProdDTO;
        
    }

    @Override
    public List<VentaProdDTO> getAllVenta() {
        // Obtener todas las ventas de la base de datos
        List<Venta> listaVen = venRepo.findAll();

        // Crear una lista vacía de VentaProdDTO para almacenar las ventas convertidas
        List<VentaProdDTO> listaVenDTO = new ArrayList<>();

        // Iterar sobre cada venta en la lista de ventas obtenida
        listaVen.forEach(venta -> {
            // Convertir la entidad Venta a un objeto VentaProdDTO
            VentaProdDTO ventaProdDTO = convertirAVentaProdDTO(venta);

            // Agregar el objeto VentaProdDTO a la lista listaVenDTO
            listaVenDTO.add(ventaProdDTO);
        });

        // Devolver la lista de VentaProdDTO
        return listaVenDTO;
        
    }

    @Override
    public VentaProdDTO getByIdVenta(Long id) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        Optional<Venta> optionalVenta = venRepo.findById(id);
        if (optionalVenta.isEmpty()) {
            throw new EntityNotFoundException("Venta no encontrada con ID: " + id);
        }
        Venta venta = optionalVenta.get();
        return convertirAVentaProdDTO(venta);
       
    }

    @Override
    public void deleteVenta(Long id) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        //verificamos si existe el cliente con ese id
        if (!venRepo.existsById(id)) {
            throw new ResourceNotFoundException("Venta no encontrado con el ID: " + id);
        }

        venRepo.deleteById(id);
    }

    @Override
    public VentaProdDTO editVenta(Long id, Venta venta) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        //verificamos si existe el cliente con ese id
        Optional<Venta> optionalVenta = venRepo.findById(id);
        if (optionalVenta.isEmpty()) {
            throw new EntityNotFoundException("Venta no encontrada con ID: " + id);
        }
        Venta ventaExistente = optionalVenta.get();
        
        // Actualizar los campos de la venta existente
        LocalDate fechaVenta = venta.getFecha_venta() != null ? venta.getFecha_venta() : LocalDate.now();
        ventaExistente.setFecha_venta(fechaVenta);
        ventaExistente.setCliente(venta.getCliente());
        ventaExistente.setListaProd(venta.getListaProd());

        // Calcular el total nuevamente si la lista de productos se ha actualizado
        double total = 0.0;
        for (Producto producto : ventaExistente.getListaProd()) {
            Double costoProducto = proRepo.findCostoById(producto.getId_product());
            if (costoProducto != null) {
                total += costoProducto;
            } else {
                System.out.println("Producto con ID " + producto.getId_product() + " no encontrado o tiene un costo nulo.");
            }
        }
        ventaExistente.setTotal(total);

        // Guardar la venta actualizada
        Venta ven = venRepo.save(ventaExistente);

        // Convertir la venta actualizada a VentaProdDTO y devolverla
        return convertirAVentaProdDTO(ven);
        
    }

    @Override
    public ResumenVentasDTO obtenerResumenVentas(LocalDate fecha) {
        List<Venta> listaVen = venRepo.findAll();
        List<Venta> ventasFiltradas = new ArrayList<>();
        
        Double totalMonto = 0.0;
        int totalVentas = 0;
        
        for (Venta venta : listaVen) {
            if (venta.getFecha_venta() != null && venta.getFecha_venta().isEqual(fecha)) {
                ventasFiltradas.add(venta);
                
                totalMonto += venta.getTotal();// acumulo y sumo el total de las ventas
                totalVentas++; //acumulo el numero de ventas que se iteran
            }
        }
        
        ResumenVentasDTO resumen = new ResumenVentasDTO();
        resumen.setTotalMonto(totalMonto);
        resumen.setTotalVentas(totalVentas);
        return resumen;
    }

}
