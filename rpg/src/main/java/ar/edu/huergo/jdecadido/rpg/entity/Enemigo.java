package ar.edu.huergo.jdecadido.rpg.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enemigo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enemigo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del enemigo es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "La vida máxima es obligatoria")
    @Min(value = 1, message = "La vida máxima mínima es 1")
    private int vidaMax;

    @NotNull(message = "La vida actual es obligatoria")
    @Min(value = 0, message = "La vida actual no puede ser negativa")
    private int vidaActual;

    @NotNull(message = "El nivel es obligatorio")
    @Min(value = 1, message = "El nivel mínimo es 1")
    private int nivel;

    @OneToMany(mappedBy = "enemigo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Atributo> atributos = new ArrayList<>();

    /**
     * Método auxiliar para verificar si el enemigo está vivo
     */
    public boolean estaVivo() {
        return this.vidaActual > 0;
    }

    /**
     * Método auxiliar para recibir daño
     */
    public void recibirDanio(int danio) {
        this.vidaActual = Math.max(0, this.vidaActual - danio);
    }

    /**
     * Método auxiliar para curar
     */
    public void curar(int cantidad) {
        this.vidaActual = Math.min(this.vidaMax, this.vidaActual + cantidad);
    }

    /**
     * Método auxiliar para restaurar vida completa
     */
    public void restaurarVida() {
        this.vidaActual = this.vidaMax;
    }
}