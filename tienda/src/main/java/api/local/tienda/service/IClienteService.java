package api.local.tienda.service;

import api.local.tienda.dto.ClienteDTO;
import api.local.tienda.dto.ClienteRequestDTO;
import api.local.tienda.dto.ClienteResponseDTO;

import java.util.List;

public interface IClienteService {
    String crear(ClienteRequestDTO request);
    ClienteResponseDTO editar(Long id, ClienteRequestDTO request);
    ClienteDTO traerPorId(Long id);

    void eliminar(Long id);

    List<ClienteDTO> listaClientes();
}
