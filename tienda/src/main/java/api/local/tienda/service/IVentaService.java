package api.local.tienda.service;

import api.local.tienda.dto.MayorVentaDTO;
import api.local.tienda.dto.ResumenVentasDTO;
import api.local.tienda.dto.VentaRequestDTO;
import api.local.tienda.dto.VentaResponseDTO;
import api.local.tienda.model.Venta;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    VentaResponseDTO crearVenta(VentaRequestDTO dto);
    VentaResponseDTO editarVenta(Integer codigoVenta, @Valid VentaRequestDTO dto);
    Venta obtenerVentaPorCodigo(Integer codigoVenta);
    List<VentaResponseDTO> obtenerVentas();
    void eliminarVenta(Integer codigoVenta);
    VentaResponseDTO detalleVenta(Integer codigoVenta);
    ResumenVentasDTO obtenerResumenVentasPorFecha(LocalDate fecha);
    MayorVentaDTO obtenerMayorVenta();
}
