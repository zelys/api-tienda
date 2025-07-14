package api.local.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
        String apellido,

        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 10, message = "El DNI debe tener entre 9 y 10 caracteres")
        String dni
) {
}
