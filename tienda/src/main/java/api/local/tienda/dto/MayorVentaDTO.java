package api.local.tienda.dto;

public record MayorVentaDTO(
        Integer codigo,
        Double total,
        Long cantidad,
        String cliente
) {}

