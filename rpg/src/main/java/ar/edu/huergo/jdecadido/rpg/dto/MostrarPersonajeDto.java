package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MostrarPersonajeDto extends PersonajeDto {
    
    public MostrarPersonajeDto(Long id , String nombre , int nivel , int xp , List<AtributoDto> atributos, List<InventarioDto> inventario){
        super(id, nombre, nivel, xp, atributos, inventario);
    }
}
