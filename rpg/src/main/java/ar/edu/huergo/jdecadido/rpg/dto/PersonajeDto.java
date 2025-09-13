package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.HashMap;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract  class PersonajeDto{


    Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull
    private int nivel;

    @NotNull
    @Min(0)
    @Max(100)
    private int xp;

    @NotNull
    @NotEmpty
    private HashMap<String , Integer> atributos;//temporal

    private HashMap<String , String> inventario;//temporal
}