package api.local.tienda.dto;

public record ClienteResponseDTO(
        Long id,
        String nombre,
        String creado_en,
        String actualizado_en
) {
}