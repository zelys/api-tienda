package api.local.tienda.controller;

import api.local.tienda.dto.MayorVentaDTO;
import api.local.tienda.dto.ResumenVentasDTO;
import api.local.tienda.dto.VentaRequestDTO;
import api.local.tienda.dto.VentaResponseDTO;
import api.local.tienda.service.IVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@Tag(name = "Ventas", description = "Operaciones sobre ventas")
@RequiredArgsConstructor
public class VentaController {

    private final IVentaService ventaService;

    @PostMapping("/crear")
    @Operation(summary = "Registrar una venta",
            description = "Crea una nueva venta y devuelve la entidad con su código generado")
    public ResponseEntity<VentaResponseDTO> crearVenta(
            @RequestBody @Valid VentaRequestDTO dto) {
        VentaResponseDTO respuesta = ventaService.crearVenta(dto);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping()

    @Operation(summary = "Listar ventas", description = "Devuelve todas las ventas registradas")
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {
        return ResponseEntity.ok(ventaService.obtenerVentas());
    }

    @PutMapping("/editar/{codigo_venta}")
    @Operation(summary = "Editar venta",
            description = "Actualiza la venta indicada y devuelve la venta modificada")
    public ResponseEntity<VentaResponseDTO> editarVenta(
            @PathVariable Integer codigo_venta,
            @RequestBody @Valid VentaRequestDTO dto) {
        VentaResponseDTO respuesta = ventaService.editarVenta(codigo_venta, dto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/eliminar/{codigo_venta}")
    @Operation(summary = "Eliminar venta",
            description = "Borra la venta indicada y devuelve mensaje de éxito")
    public ResponseEntity<String> eliminarVenta(
            @PathVariable Integer codigo_venta) {
        ventaService.eliminarVenta(codigo_venta);
        return ResponseEntity.ok("Registro eliminado correctamente");
    }

    @GetMapping("/detalle/{codigo_venta}")
    @Operation(summary = "Detalle de venta por código",
            description = "Devuelve la venta (con detalles) cuyo código coincide")
    public ResponseEntity<VentaResponseDTO> detalleVenta(
            @PathVariable Integer codigo_venta) {
        return ResponseEntity.ok(ventaService.detalleVenta(codigo_venta));
    }

    @GetMapping("/resumen")
    @Operation(summary = "Resumen de ventas por fecha",
            description = "Devuelve total y cantidad de ventas de la fecha indicada")
    public ResponseEntity<ResumenVentasDTO> obtenerResumenVentasPorFecha(
            @RequestParam("fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {
        try {
            ResumenVentasDTO resumen = ventaService.obtenerResumenVentasPorFecha(fecha);
            return ResponseEntity.ok(resumen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mayor-venta")
    @Operation(summary = "Venta con mayor factura",
            description = "Devuelve la venta con el mayor total facturado")
    public ResponseEntity<MayorVentaDTO> mayorVenta() {
        return ResponseEntity.ok(ventaService.obtenerMayorVenta());
    }
}

