package api.local.tienda.dto;

public record ProductoResponseDTO(
        Integer codigo,
        String nombre,
        String marca,
        Double costo,
        Integer cantidad_disponible
) {}
