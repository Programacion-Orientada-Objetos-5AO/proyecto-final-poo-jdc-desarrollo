package ar.edu.huergo.jdecadido.rpg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostrarEnemigoDto {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private int vidaMax;
    private int vidaActual;
    private int nivel;
    private boolean estaVivo;
    private List<AtributoDto> atributos;
}