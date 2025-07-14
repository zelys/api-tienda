package api.local.tienda.dto;

public record ClienteDTO(
        Long id,
        String nombre,
        String apellido,
        String dni
) {
}
