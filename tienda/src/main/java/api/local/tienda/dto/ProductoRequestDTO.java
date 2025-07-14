package api.local.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoRequestDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String marca,
        @NotNull
        @PositiveOrZero
        Double costo,
        @NotNull
        @PositiveOrZero
        Integer cantidad_disponible
) {}
