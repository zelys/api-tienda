package api.local.tienda.dto;

import java.util.List;

public record VentaResponseDTO(
        Integer codigo,
        String fecha,
        String hora,
        String cliente,
        Double total,
        List<DetalleResponseDTO> detalles
) {
    public record DetalleResponseDTO(
            ProductoDTO producto,
            Double precio,
            Integer cantidad,
            Double subtotal
    ) {}
}
