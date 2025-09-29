package ar.edu.huergo.jdecadido.rpg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPersonajeDto {
    private String nombre;
    private int nivel;
    private int xp;
}