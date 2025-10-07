package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostrarPersonajeDto {
    
    private Long id;
    private String nombre;
    private int nivel;
    private int xp;
    private int vidaMax;
    private int vidaActual;
    private boolean estaVivo;
    private List<AtributoDto> atributos;
    private List<InventarioDto> inventario;
}