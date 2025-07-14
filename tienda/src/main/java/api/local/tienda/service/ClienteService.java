package api.local.tienda.service;

import api.local.tienda.dto.ClienteDTO;
import api.local.tienda.dto.ClienteRequestDTO;
import api.local.tienda.dto.ClienteResponseDTO;
import api.local.tienda.exception.BusinessException;
import api.local.tienda.exception.DuplicateResourceException;
import api.local.tienda.exception.ResourceNotFoundException;
import api.local.tienda.model.Cliente;
import api.local.tienda.repository.IClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService{

    private final IClienteRepository repo;

    @Override
    @Transactional
    public String crear(ClienteRequestDTO request) {
        validarDni(request.dni());
        repo.save(mapToRecuestDTO(request));
        return "Cliente creado exitosamente";
    }

    @Override
    public ClienteDTO traerPorId(Long id) {
        return mapToClienteDTO(traerCliente(id));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        validarId(id);
        validarCompras(id);
        repo.deleteById(id);
    }

    @Override
    public List<ClienteDTO> listaClientes() {
        return repo.findAll().stream()
                .map(this::mapToClienteDTO).toList();
    }

    @Override
    @Transactional
    public ClienteResponseDTO editar(Long id, ClienteRequestDTO request) {
        Cliente c = traerCliente(id);
        boolean otroDni = !c.getDni().equals(request.dni().trim());

        if (otroDni) {
            validarDni(request.dni());
            c.setDni(request.dni());
        }
        c.setNombre(request.nombre());
        c.setApellido(request.apellido());
        repo.save(c);
        return mapToResponse(traerCliente(id));
    }

    //
    // MÉTODOS DE LA CLASE
    //
    private Cliente traerCliente(Long id) {
        validarId(id);
        return repo.findById(id).orElse(null);
    }

    private ClienteResponseDTO mapToResponse(Cliente c) {
        return new ClienteResponseDTO(
                c.getIdCliente(),
                c.getNombre() + " " + c.getApellido(),
                formatearDateTime(c.getCreadoEn()),
                formatearDateTime(c.getActualizadoEn())
        );
    }

    private  ClienteDTO mapToClienteDTO(Cliente c) {
        return new ClienteDTO(
                c.getIdCliente(),
                c.getNombre(),
                c.getApellido(),
                c.getDni()
        );
    }

    private Cliente mapToRecuestDTO(ClienteRequestDTO request) {
        Cliente c = new Cliente();
        c.setNombre(request.nombre().trim());
        c.setApellido(request.apellido().trim());
        c.setDni(request.dni().trim());
        return c;
    }

    private String formatearDateTime(LocalDateTime fecha) {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private void validarId(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado");
        }
    }

    private void validarDni(String dni) {
        if (repo.existsByDni(dni)) {
            throw new DuplicateResourceException("Ya existe otro cliente con el DNI: " + dni);
        }
    }

    private void validarCompras(Long id) {
        if (repo.tieneVentasAsociadas(id)) {
            throw new BusinessException("No se puede eliminar el cliente porque tiene ventas asociadas");
        }
    }
}
