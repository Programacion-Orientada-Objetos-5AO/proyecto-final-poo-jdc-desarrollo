package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.HashMap;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MostrarPersonajeDto extends PersonajeDto {
    
    public MostrarPersonajeDto(Long id , String nombre , int nivel , int xp , HashMap<String , Integer> atributos , HashMap<String , String> inventario){
        super(id, nombre, nivel, xp, atributos, inventario);
    }
}
