package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.Personaje;
import ar.edu.huergo.jdecadido.rpg.repository.PersonajeRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PersonajeService {
    
    @Autowired
    private PersonajeRepository personajeRepository;

    public List<Personaje> obtenerTodosLosPersonajes(){
        return personajeRepository.findAll();
    }

    public Personaje obtenerPersonajePorId(Long id) throws EntityNotFoundException{
        return personajeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));
    }

    public Personaje crearPersonaje(Personaje personaje) {
        return personajeRepository.save(personaje);
    }

    public Personaje actualizarPersonaje(Long id, Personaje personaje) throws EntityNotFoundException {
        Personaje personajeExistente = obtenerPersonajePorId(id);
        personajeExistente.setNombre(personaje.getNombre());
        personajeExistente.setNivel(personaje.getNivel());
        personajeExistente.setXp(personaje.getXp());
        personajeExistente.getAtributos().clear();
        personaje.getAtributos().forEach(a -> a.setPersonaje(personajeExistente));
        personajeExistente.getAtributos().addAll(personaje.getAtributos());
        return personajeRepository.save(personajeExistente);
    }

    public void eliminarPersonaje(Long id) throws EntityNotFoundException {
        Personaje personaje = obtenerPersonajePorId(id);
        personajeRepository.delete(personaje);
    }
}
