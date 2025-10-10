package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonajeDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El nivel es obligatorio")
    @Min(value = 1, message = "El nivel mínimo es 1")
    private int nivel;

    @NotNull(message = "La experiencia es obligatoria")
    @Min(value = 0, message = "La experiencia no puede ser negativa")
    @Max(value = 100, message = "La experiencia máxima es 100")
    private int xp;

    @NotNull(message = "La vida máxima es obligatoria")
    @Min(value = 1, message = "La vida máxima mínima es 1")
    private int vidaMax;

    @NotNull(message = "La vida actual es obligatoria")
    @Min(value = 0, message = "La vida actual no puede ser negativa")
    private int vidaActual;

    private boolean estaVivo;

    private List<AtributoDto> atributos;
    
    private List<ItemInventarioDto> inventario;
}