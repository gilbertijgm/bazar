 
package com.todocode.proyectofinal.service;

import com.todocode.proyectofinal.dto.ClienteDTO;
import com.todocode.proyectofinal.model.Cliente;
import java.util.List;

 
public interface IClienteService {
    
    public Cliente guardarCliente(Cliente cliente);
    
    public List<ClienteDTO> getAllCli();
    
    public ClienteDTO getClienteById(Long id);
    
    public void deleteCliente(Long id);
    
    public Cliente editCliente(Long id, Cliente cliente);
}
