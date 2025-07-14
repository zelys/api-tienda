package api.local.tienda.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record VentaRequestDTO(
        @NotNull
        Long id_cliente,
        @NotNull @Size(min = 1)
        List<DetalleRequestDTO> detalles
) {
    public record DetalleRequestDTO(
            @NotNull
            Integer codigo_producto,
            @NotNull @Positive
            Integer cantidad
    ) {}
}
