package ar.edu.huergo.jdecadido.rpg.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.jdecadido.rpg.dto.CrearPersonajeDto;
import ar.edu.huergo.jdecadido.rpg.dto.MostrarPersonajeDto;
import ar.edu.huergo.jdecadido.rpg.entity.Personaje;
import ar.edu.huergo.jdecadido.rpg.mapper.PersonajeMapper;
import ar.edu.huergo.jdecadido.rpg.service.PersonajeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {
    
    @Autowired
    private PersonajeService personajeService;

    @Autowired
    private PersonajeMapper personajeMapper;

    /**
     * Obtiene todos los personajes
     * GET /api/personajes
     */
    @GetMapping
    public ResponseEntity<List<MostrarPersonajeDto>> obtenerTodosLosPersonajes() {
        List<Personaje> personajes = personajeService.obtenerTodosLosPersonajes();
        List<MostrarPersonajeDto> personajesDto = personajeMapper.toDTOList(personajes);
        return ResponseEntity.ok(personajesDto);
    }

    /**
     * Obtiene un personaje por ID
     * GET /api/personajes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MostrarPersonajeDto> obtenerPersonajePorId(@PathVariable Long id) {
        Personaje personaje = personajeService.obtenerPersonajePorId(id);
        MostrarPersonajeDto personajeDto = personajeMapper.toDTO(personaje);
        return ResponseEntity.ok(personajeDto);
    }

    /**
     * Crea un nuevo personaje
     * POST /api/personajes
     */
    @PostMapping
    public ResponseEntity<MostrarPersonajeDto> crearPersonaje(@Valid @RequestBody CrearPersonajeDto personajeDto) {
        Personaje personaje = personajeMapper.toEntity(personajeDto);
        Personaje personajeCreado = personajeService.crearPersonaje(personaje);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personajeCreado.getId())
                .toUri();
        return ResponseEntity.created(location).body(personajeMapper.toDTO(personajeCreado));
    }

    /**
     * Actualiza un personaje existente
     * PUT /api/personajes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<MostrarPersonajeDto> actualizarPersonaje(
            @PathVariable Long id,
            @Valid @RequestBody CrearPersonajeDto personajeDto) {
        Personaje personaje = personajeMapper.toEntity(personajeDto);
        Personaje personajeActualizado = personajeService.actualizarPersonaje(id, personaje);
        return ResponseEntity.ok(personajeMapper.toDTO(personajeActualizado));
    }

    /**
     * Elimina un personaje
     * DELETE /api/personajes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPersonaje(@PathVariable Long id) {
        personajeService.eliminarPersonaje(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Aplica daño a un personaje
     * POST /api/personajes/{id}/danio?cantidad=20
     */
    @PostMapping("/{id}/danio")
    public ResponseEntity<MostrarPersonajeDto> aplicarDanio(
            @PathVariable Long id,
            @RequestParam int cantidad) {
        Personaje personaje = personajeService.aplicarDanio(id, cantidad);
        return ResponseEntity.ok(personajeMapper.toDTO(personaje));
    }

    /**
     * Cura a un personaje
     * POST /api/personajes/{id}/curar?cantidad=15
     */
    @PostMapping("/{id}/curar")
    public ResponseEntity<MostrarPersonajeDto> curarPersonaje(
            @PathVariable Long id,
            @RequestParam int cantidad) {
        Personaje personaje = personajeService.curarPersonaje(id, cantidad);
        return ResponseEntity.ok(personajeMapper.toDTO(personaje));
    }

    /**
     * Restaura la vida completa de un personaje
     * POST /api/personajes/{id}/restaurar
     */
    @PostMapping("/{id}/restaurar")
    public ResponseEntity<MostrarPersonajeDto> restaurarVida(@PathVariable Long id) {
        Personaje personaje = personajeService.restaurarVida(id);
        return ResponseEntity.ok(personajeMapper.toDTO(personaje));
    }
}