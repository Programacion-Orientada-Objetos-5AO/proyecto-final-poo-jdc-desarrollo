
package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearEnemigoDto {
    
    @NotBlank(message = "El nombre del enemigo es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "La vida máxima es obligatoria")
    @Min(value = 1, message = "La vida máxima mínima es 1")
    private int vidaMax;
    
    @NotNull(message = "El nivel es obligatorio")
    @Min(value = 1, message = "El nivel mínimo es 1")
    private int nivel;
    
    
    @Valid
    private List<AtributoDto> atributos = new ArrayList<>();
}