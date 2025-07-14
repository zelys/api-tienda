package api.local.tienda.dto;

import java.time.LocalDate;

public record ResumenVentasDTO(
        LocalDate fecha,
        Double monto_total,
        Long cantidad_ventas
) {
}
