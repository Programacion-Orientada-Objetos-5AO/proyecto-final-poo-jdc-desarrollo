package ar.edu.huergo.jdecadido.rpg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.jdecadido.rpg.entity.Enemigo;

@Repository
public interface EnemigoRepository extends JpaRepository<Enemigo, Long> {
    
    /**
     * Busca enemigos por nombre (ignorando mayúsculas/minúsculas)
     */
    List<Enemigo> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca enemigos por nivel
     */
    List<Enemigo> findByNivel(int nivel);
    
    /**
     * Busca enemigos por rango de nivel
     */
    List<Enemigo> findByNivelBetween(int nivelMin, int nivelMax);
}