package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.HashMap;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MostrarPersonajeDto extends PersonajeDto {
    
    public MostrarPersonajeDto(Long id , String nombre , int nivel , int xp , HashMap<String , Integer> atributos , HashMap<String , String> inventario){
        super(id, nombre, nivel, xp, atributos, inventario);
    }
}
