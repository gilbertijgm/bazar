package com.todocode.proyectofinal.controller;

import com.todocode.proyectofinal.dto.ClienteDTO;
import com.todocode.proyectofinal.exception.DuplicateResourceException;
import com.todocode.proyectofinal.exception.ResourceNotFoundException;

import com.todocode.proyectofinal.model.Cliente;
import com.todocode.proyectofinal.service.IClienteService;
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
public class ClienteController {

    @Autowired
    private IClienteService cliServi;

    @PostMapping("/cliente/crear")
    public ResponseEntity<?> guardarCliente(@Validated @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            // Recopilar todos los mensajes de error
            String errorMessage = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            // Devolver los errores en el cuerpo de la respuesta con un código de estado 400
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Cliente cli = cliServi.guardarCliente(cliente);

        return new ResponseEntity<>(cli, HttpStatus.CREATED);

    }

    @GetMapping("/cliente/todos")
    public ResponseEntity<?> getClientes() {
        List<ClienteDTO> clientes = cliServi.getAllCli();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/{id_cliente}")
    public ResponseEntity<?> getCliById(@PathVariable Long id_cliente) {
        ClienteDTO cliente = cliServi.getClienteById(id_cliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/cliente/edit/{id_cliente}")
    public ResponseEntity<?> editarCliente(@PathVariable Long id_cliente,
            @Validated @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        Cliente cliEdit = cliServi.editCliente(id_cliente, cliente);
        return new ResponseEntity<>(cliEdit, HttpStatus.OK);
    }

    @DeleteMapping("/cliente/delete/{id_cliente}")
    public ResponseEntity<String> deleteCli(@PathVariable Long id_cliente) {
        cliServi.deleteCliente(id_cliente);
        return new ResponseEntity<>("Cliente eliminado con éxito.", HttpStatus.OK);
    }
}
