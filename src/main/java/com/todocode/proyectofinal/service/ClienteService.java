package com.todocode.proyectofinal.service;

import com.todocode.proyectofinal.dto.ClienteDTO;
import com.todocode.proyectofinal.exception.DuplicateResourceException;
import com.todocode.proyectofinal.exception.ResourceNotFoundException;
import com.todocode.proyectofinal.model.Cliente;
import com.todocode.proyectofinal.repository.IClienteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository cliRepo;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        // Verificar si el DNI ya existe
        if (cliRepo.existsByDni(cliente.getDni())) {
            throw new DuplicateResourceException("El dni ya esta registrado");
        }
        Cliente client = cliRepo.save(cliente);

        return client;
    }

    @Override
    public List<ClienteDTO> getAllCli() {
        List<Cliente> listaCli = cliRepo.findAll();
        if (listaCli.isEmpty()) {
            // Manejar el caso de lista vacía si es necesario
            // Puedes devolver una lista vacía en lugar de lanzar una excepción
            // return new ArrayList<>();   
            throw new ResourceNotFoundException("No se encontraron clientes.");
        }
        
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : listaCli) {
            ClienteDTO dto = new ClienteDTO();
            dto.setId_cliente(cliente.getId_cliente());
            dto.setDni(cliente.getDni());
            dto.setNomApe(cliente.getNomApe());
            clientesDTO.add(dto);
        }
        return clientesDTO;
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        //busca el cliente por id si no lo encuentra envia un mensaje
        Cliente cli = cliRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));
        
         ClienteDTO cliDto = new ClienteDTO();
         cliDto.setId_cliente(cli.getId_cliente());
         cliDto.setDni(cli.getDni());
         cliDto.setNomApe(cli.getNomApe());
         
        return cliDto;
    }

    @Override
    public void deleteCliente(Long id) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        //verificamos si existe el cliente con ese id
        if (!cliRepo.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con el ID: " + id);
        }

        cliRepo.deleteById(id);
    }

    public Cliente editCliente(Long id, Cliente cliente) {
        //verificar el id sea valido y no sea nulo
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        //busca el cliente por id si no lo encuentra envia un mensaje
        Cliente cliEdit = cliRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el ID: " + id));

        // Validar que el DNI sea único, excluyendo el cliente actual
        if (cliente.getDni() != null && !cliente.getDni().equals(cliEdit.getDni())
                && cliRepo.existsByDni(cliente.getDni())) {

            throw new DuplicateResourceException("El DNI ya está registrado.");
        }

        // Actualizar los campos del cliente existente con los datos del cliente recibido
        cliEdit.setDni(cliente.getDni());
        cliEdit.setNomApe(cliente.getNomApe());

        // Actualizar otros campos según sea necesario
        // Guardar el cliente actualizado
        cliRepo.save(cliEdit);
        return cliEdit;

    }

}
