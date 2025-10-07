
package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.Personaje;
import ar.edu.huergo.jdecadido.rpg.repository.PersonajeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PersonajeService {
    
    @Autowired
    private PersonajeRepository personajeRepository;

    /**
     * Obtiene todos los personajes
     */
    public List<Personaje> obtenerTodosLosPersonajes() {
        return personajeRepository.findAll();
    }

    /**
     * Obtiene un personaje por ID
     */
    public Personaje obtenerPersonajePorId(Long id) throws EntityNotFoundException {
        return personajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + id));
    }

    /**
     * Crea un nuevo personaje
     */
    @Transactional
    public Personaje crearPersonaje(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    /**
     * Actualiza un personaje existente
     */
    @Transactional
    public Personaje actualizarPersonaje(Long id, Personaje personaje) throws EntityNotFoundException {
        Personaje personajeExistente = obtenerPersonajePorId(id);
        
        personajeExistente.setNombre(personaje.getNombre());
        personajeExistente.setNivel(personaje.getNivel());
        personajeExistente.setXp(personaje.getXp());
        personajeExistente.setVidaMax(personaje.getVidaMax());
        personajeExistente.setVidaActual(personaje.getVidaActual());
        
        // Actualizar atributos
        personajeExistente.getAtributos().clear();
        if (personaje.getAtributos() != null) {
            personaje.getAtributos().forEach(a -> a.setPersonaje(personajeExistente));
            personajeExistente.getAtributos().addAll(personaje.getAtributos());
        }
        
        return personajeRepository.save(personajeExistente);
    }

    /**
     * Elimina un personaje
     */
    @Transactional
    public void eliminarPersonaje(Long id) throws EntityNotFoundException {
        Personaje personaje = obtenerPersonajePorId(id);
        personajeRepository.delete(personaje);
    }

    /**
     * Aplica daño a un personaje
     */
    @Transactional
    public Personaje aplicarDanio(Long id, int danio) throws EntityNotFoundException {
        Personaje personaje = obtenerPersonajePorId(id);
        personaje.recibirDanio(danio);
        return personajeRepository.save(personaje);
    }

    /**
     * Cura a un personaje
     */
    @Transactional
    public Personaje curarPersonaje(Long id, int cantidad) throws EntityNotFoundException {
        Personaje personaje = obtenerPersonajePorId(id);
        personaje.curar(cantidad);
        return personajeRepository.save(personaje);
    }

    /**
     * Restaura la vida completa de un personaje
     */
    @Transactional
    public Personaje restaurarVida(Long id) throws EntityNotFoundException {
        Personaje personaje = obtenerPersonajePorId(id);
        personaje.restaurarVida();
        return personajeRepository.save(personaje);
    }
}