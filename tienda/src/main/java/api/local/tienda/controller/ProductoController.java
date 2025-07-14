package api.local.tienda.controller;

import api.local.tienda.dto.*;
import api.local.tienda.model.Producto;
import api.local.tienda.service.IProductoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Operaciones sobre productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    @PostMapping("/crear")
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto y devuelve un mensaje de éxito")
    public ResponseEntity<String> crear(@RequestBody @Valid ProductoRequestDTO dto) {
        productoService.crear(dto);
        return ResponseEntity.ok("Producto creado correctamente.");
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Devuelve una lista de todos los productos")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{codigo_producto}")
    @Operation(summary = "Obtener producto por código", description = "Devuelve un producto basado en su código")
    public ResponseEntity<ProductoResponseDTO> traerPorCodigo(@PathVariable Integer codigo) {
        return ResponseEntity.ok(productoService.traerPorCodigo(codigo));
    }

    @PutMapping("/editar/{codigo_producto}")
    @Operation(summary = "Editar producto", description = "Edita un producto existente y devuelve un mensaje de éxito")
    public ResponseEntity<String> editar(@PathVariable Integer codigo, @RequestBody @Valid ProductoRequestDTO dto) {
        productoService.editar(codigo, dto);
        return ResponseEntity.ok("Producto actualizado correctamente.");
    }

    @DeleteMapping("/eliminar/{codigo_producto}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto basado en su código")
    public ResponseEntity<String> eliminar(@PathVariable Integer codigo) {
        productoService.eliminar(codigo);
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }

    @GetMapping("/falta_stock")
    @Operation(summary = "Listar productos con falta de stock", description = "Devuelve productos que tienen falta de stock")
    public List<ProductoResponseDTO> listarFaltaDeStock() {
        return productoService.listarFaltaDeStock();
    }
}
