package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.HashMap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract  class PersonajeDto{


    Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El nivel es obligatorio")
    private int nivel;

    @NotBlank(message = "el xp es obligatorio")
    @Size(min = 0 , max = 100 , message = "El xp debe estar entre 0 y 100")
    private int xp;

    @NotBlank(message = "Los atributos son obligatorios")
    private HashMap<String , Integer> atributos;//temporal

    private HashMap<String , String> inventario;//temporal
}