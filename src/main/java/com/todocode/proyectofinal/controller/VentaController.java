package com.todocode.proyectofinal.controller;

import com.todocode.proyectofinal.dto.ResumenVentasDTO;
import com.todocode.proyectofinal.dto.VentaProdDTO;
import com.todocode.proyectofinal.model.Cliente;
import com.todocode.proyectofinal.model.Venta;
import com.todocode.proyectofinal.service.IVentaService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VentaController {

    @Autowired
    private IVentaService venServi;

//    @PostMapping("/venta/crear")
//    public ResponseEntity<?> guardarVenta(@Validated @RequestBody Venta venta, BindingResult result) {
//        VentaProdDTO ventaProdDTO = venServi.crearVenta(venta);
//        return new ResponseEntity<>(ventaProdDTO, HttpStatus.CREATED);
//    }
    @PostMapping("/venta/crear")
    public ResponseEntity<?> guardarVenta(@Validated @RequestBody Venta venta, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        VentaProdDTO ventaProdDTO = venServi.crearVenta(venta);
        return new ResponseEntity<>(ventaProdDTO, HttpStatus.CREATED);
    }

    @GetMapping("/venta/todo")
    public ResponseEntity<List<VentaProdDTO>> obtenerTodasLasVentas() {
        List<VentaProdDTO> listaVenDTO = venServi.getAllVenta();
        return new ResponseEntity<>(listaVenDTO, HttpStatus.OK);
    }

    @GetMapping("/venta/{id_venta}")
    public ResponseEntity<VentaProdDTO> obtenerVentaPorId(@PathVariable Long id_venta) {
        VentaProdDTO venDTO = venServi.getByIdVenta(id_venta);
        return new ResponseEntity<>(venDTO, HttpStatus.OK);
    }

    @PutMapping("/venta/editar/{id_venta}")
    public ResponseEntity<VentaProdDTO> editarVenta(@PathVariable Long id_venta,
            @Validated @RequestBody Venta venta, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        VentaProdDTO venDTO = venServi.editVenta(id_venta, venta);
        return new ResponseEntity<>(venDTO, HttpStatus.OK);
    }

    @DeleteMapping("/venta/delete/{id_venta}")
    public ResponseEntity<String> deleteCli(@PathVariable Long id_venta) {
        venServi.deleteVenta(id_venta);
        return new ResponseEntity<>("Venta eliminada con éxito.", HttpStatus.OK);
    }

    @GetMapping("/venta/resumen/{fechaVenta}")
    public ResponseEntity<ResumenVentasDTO> obtenerResumenVentasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaVenta) {
        ResumenVentasDTO resumen = venServi.obtenerResumenVentas(fechaVenta);
        return ResponseEntity.ok(resumen);
    }
}
