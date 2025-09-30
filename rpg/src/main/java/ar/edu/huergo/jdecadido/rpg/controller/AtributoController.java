package ar.edu.huergo.jdecadido.rpg.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.jdecadido.rpg.dto.AtributoDto;
import ar.edu.huergo.jdecadido.rpg.entity.Atributo;
import ar.edu.huergo.jdecadido.rpg.entity.Personaje;
import ar.edu.huergo.jdecadido.rpg.repository.PersonajeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * Controller para gestionar los atributos de un personaje específico.
 * Todos los endpoints están bajo /api/personajes/{personajeId}/atributos
 */
@RestController
@RequestMapping("/api/personajes/{personajeId}/atributos")
public class AtributoController {

    @Autowired
    private PersonajeRepository personajeRepository;

    /**
     * Obtiene todos los atributos de un personaje
     * GET /api/personajes/{personajeId}/atributos
     */
    @GetMapping
    public ResponseEntity<List<AtributoDto>> listarAtributos(@PathVariable Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
        
        List<AtributoDto> atributos = personaje.getAtributos().stream()
            .map(a -> new AtributoDto(a.getNombre(), a.getValor()))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(atributos);
    }

    /**
     * Agrega un nuevo atributo a un personaje
     * POST /api/personajes/{personajeId}/atributos
     */
    @PostMapping
    public ResponseEntity<AtributoDto> agregarAtributo(
            @PathVariable Long personajeId,
            @Valid @RequestBody AtributoDto atributoDto) {
        
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
        
        // Crear nuevo atributo y asociarlo al personaje
        Atributo atributo = new Atributo();
        atributo.setNombre(atributoDto.getNombre());
        atributo.setValor(atributoDto.getValor());
        atributo.setPersonaje(personaje);
        
        personaje.getAtributos().add(atributo);
        personajeRepository.save(personaje);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(atributo.getId())
            .toUri();
        
        return ResponseEntity.created(location).body(atributoDto);
    }

    /**
     * Actualiza un atributo específico de un personaje
     * PUT /api/personajes/{personajeId}/atributos/{atributoId}
     */
    @PutMapping("/{atributoId}")
    public ResponseEntity<AtributoDto> actualizarAtributo(
            @PathVariable Long personajeId,
            @PathVariable Long atributoId,
            @Valid @RequestBody AtributoDto atributoDto) {
        
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
        
        Atributo atributo = personaje.getAtributos().stream()
            .filter(a -> a.getId().equals(atributoId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Atributo no encontrado con id: " + atributoId));
        
        atributo.setNombre(atributoDto.getNombre());
        atributo.setValor(atributoDto.getValor());
        
        personajeRepository.save(personaje);
        
        return ResponseEntity.ok(atributoDto);
    }

    /**
     * Elimina un atributo específico de un personaje
     * DELETE /api/personajes/{personajeId}/atributos/{atributoId}
     */
    @DeleteMapping("/{atributoId}")
    public ResponseEntity<Void> eliminarAtributo(
            @PathVariable Long personajeId,
            @PathVariable Long atributoId) {
        
        Personaje personaje = personajeRepository.findById(personajeId)
            .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));
        
        boolean removed = personaje.getAtributos().removeIf(a -> a.getId().equals(atributoId));
        
        if (!removed) {
            throw new EntityNotFoundException("Atributo no encontrado con id: " + atributoId);
        }
        
        personajeRepository.save(personaje);
        
        return ResponseEntity.noContent().build();
    }
}