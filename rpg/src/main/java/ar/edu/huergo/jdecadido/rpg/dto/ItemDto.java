package ar.edu.huergo.jdecadido.rpg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private int poder;
}
