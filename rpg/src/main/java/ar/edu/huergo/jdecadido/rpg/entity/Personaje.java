package ar.edu.huergo.jdecadido.rpg.entity;

import java.util.HashMap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "personaje") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El nivel es obligatorio")
    private int nivel;

    @NotBlank(message = "el xp es obligatorio")
    @Size(min = 0 , max = 100 , message = "El xp debe estar entre 0 y 100")
    private int xp;

    @NotBlank(message = "Los atributos son obligatorios")
    private HashMap<String , Integer> atributos;

    
}
