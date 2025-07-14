package api.local.tienda.controller;


import api.local.tienda.dto.ClienteDTO;
import api.local.tienda.dto.ClienteRequestDTO;
import api.local.tienda.dto.ClienteResponseDTO;
import api.local.tienda.service.IClienteService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operaciones sobre clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService service;

    @PostMapping("/crear")
    @Operation(summary = "Crear cliente", description = "Crea un nuevo cliente y devuelve un mensaje de éxito")
    public ResponseEntity<String> crear(@Valid @RequestBody ClienteRequestDTO request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar cliente", description = "Edita un cliente existente y devuelve la entidad actualizada")
    public ResponseEntity<ClienteResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO response = service.editar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Devuelve un cliente basado en su ID")
    public ResponseEntity<ClienteDTO> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(service.traerPorId(id));
    }

    @GetMapping()
    @Operation(summary = "Listar clientes", description = "Devuelve una lista de todos los clientes")
    public ResponseEntity<List<ClienteDTO>> listaClientes() {
        return ResponseEntity.ok(service.listaClientes());
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente basado en su ID")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
