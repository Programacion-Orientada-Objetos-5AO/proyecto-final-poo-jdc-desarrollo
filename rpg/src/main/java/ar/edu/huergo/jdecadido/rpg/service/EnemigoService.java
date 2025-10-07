package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.Enemigo;
import ar.edu.huergo.jdecadido.rpg.repository.EnemigoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EnemigoService {
    
    @Autowired
    private EnemigoRepository enemigoRepository;

    /**
     * Obtiene todos los enemigos
     */
    public List<Enemigo> obtenerTodosLosEnemigos() {
        return enemigoRepository.findAll();
    }

    /**
     * Obtiene un enemigo por ID
     */
    public Enemigo obtenerEnemigoPorId(Long id) throws EntityNotFoundException {
        return enemigoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enemigo no encontrado con id: " + id));
    }

    /**
     * Busca enemigos por nombre
     */
    public List<Enemigo> buscarPorNombre(String nombre) {
        return enemigoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca enemigos por nivel
     */
    public List<Enemigo> buscarPorNivel(int nivel) {
        return enemigoRepository.findByNivel(nivel);
    }

    /**
     * Busca enemigos por rango de nivel
     */
    public List<Enemigo> buscarPorRangoNivel(int nivelMin, int nivelMax) {
        return enemigoRepository.findByNivelBetween(nivelMin, nivelMax);
    }

    /**
     * Crea un nuevo enemigo
     */
    @Transactional
    public Enemigo crearEnemigo(Enemigo enemigo) {
        return enemigoRepository.save(enemigo);
    }

    /**
     * Actualiza un enemigo existente
     */
    @Transactional
    public Enemigo actualizarEnemigo(Long id, Enemigo enemigo) throws EntityNotFoundException {
        Enemigo enemigoExistente = obtenerEnemigoPorId(id);
        
        enemigoExistente.setNombre(enemigo.getNombre());
        enemigoExistente.setDescripcion(enemigo.getDescripcion());
        enemigoExistente.setVidaMax(enemigo.getVidaMax());
        enemigoExistente.setVidaActual(enemigo.getVidaActual());
        enemigoExistente.setNivel(enemigo.getNivel());
        
        // Actualizar atributos
        enemigoExistente.getAtributos().clear();
        if (enemigo.getAtributos() != null) {
            enemigo.getAtributos().forEach(a -> a.setEnemigo(enemigoExistente));
            enemigoExistente.getAtributos().addAll(enemigo.getAtributos());
        }
        
        return enemigoRepository.save(enemigoExistente);
    }

    /**
     * Elimina un enemigo
     */
    @Transactional
    public void eliminarEnemigo(Long id) throws EntityNotFoundException {
        Enemigo enemigo = obtenerEnemigoPorId(id);
        enemigoRepository.delete(enemigo);
    }

    /**
     * Aplica daño a un enemigo
     */
    @Transactional
    public Enemigo aplicarDanio(Long id, int danio) throws EntityNotFoundException {
        Enemigo enemigo = obtenerEnemigoPorId(id);
        enemigo.recibirDanio(danio);
        return enemigoRepository.save(enemigo);
    }

    /**
     * Cura a un enemigo
     */
    @Transactional
    public Enemigo curarEnemigo(Long id, int cantidad) throws EntityNotFoundException {
        Enemigo enemigo = obtenerEnemigoPorId(id);
        enemigo.curar(cantidad);
        return enemigoRepository.save(enemigo);
    }

    /**
     * Restaura la vida completa de un enemigo
     */
    @Transactional
    public Enemigo restaurarVida(Long id) throws EntityNotFoundException {
        Enemigo enemigo = obtenerEnemigoPorId(id);
        enemigo.restaurarVida();
        return enemigoRepository.save(enemigo);
    }
}