package ar.edu.huergo.jdecadido.rpg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDto {
    
    private Long itemId;
    private String nombreItem; 
    private int cantidad;
    private boolean equipado;
}
