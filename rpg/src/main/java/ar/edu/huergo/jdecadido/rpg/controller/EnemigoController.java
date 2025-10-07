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

import ar.edu.huergo.jdecadido.rpg.dto.CrearEnemigoDto;
import ar.edu.huergo.jdecadido.rpg.dto.MostrarEnemigoDto;
import ar.edu.huergo.jdecadido.rpg.entity.Enemigo;
import ar.edu.huergo.jdecadido.rpg.mapper.EnemigoMapper;
import ar.edu.huergo.jdecadido.rpg.service.EnemigoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/enemigos")
public class EnemigoController {
    
    @Autowired
    private EnemigoService enemigoService;

    @Autowired
    private EnemigoMapper enemigoMapper;

    /**
     * Obtiene todos los enemigos
     * GET /api/enemigos
     */
    @GetMapping
    public ResponseEntity<List<MostrarEnemigoDto>> obtenerTodosLosEnemigos() {
        List<Enemigo> enemigos = enemigoService.obtenerTodosLosEnemigos();
        List<MostrarEnemigoDto> enemigosDto = enemigoMapper.toDTOList(enemigos);
        return ResponseEntity.ok(enemigosDto);
    }

    /**
     * Obtiene un enemigo por ID
     * GET /api/enemigos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MostrarEnemigoDto> obtenerEnemigoPorId(@PathVariable Long id) {
        Enemigo enemigo = enemigoService.obtenerEnemigoPorId(id);
        MostrarEnemigoDto enemigoDto = enemigoMapper.toDTO(enemigo);
        return ResponseEntity.ok(enemigoDto);
    }

    /**
     * Busca enemigos por nombre
     * GET /api/enemigos/buscar?nombre=goblin
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<MostrarEnemigoDto>> buscarPorNombre(@RequestParam String nombre) {
        List<Enemigo> enemigos = enemigoService.buscarPorNombre(nombre);
        List<MostrarEnemigoDto> enemigosDto = enemigoMapper.toDTOList(enemigos);
        return ResponseEntity.ok(enemigosDto);
    }

    /**
     * Busca enemigos por nivel
     * GET /api/enemigos/nivel/5
     */
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<MostrarEnemigoDto>> buscarPorNivel(@PathVariable int nivel) {
        List<Enemigo> enemigos = enemigoService.buscarPorNivel(nivel);
        List<MostrarEnemigoDto> enemigosDto = enemigoMapper.toDTOList(enemigos);
        return ResponseEntity.ok(enemigosDto);
    }

    /**
     * Crea un nuevo enemigo
     * POST /api/enemigos
     */
    @PostMapping
    public ResponseEntity<MostrarEnemigoDto> crearEnemigo(@Valid @RequestBody CrearEnemigoDto enemigoDto) {
        Enemigo enemigo = enemigoMapper.toEntity(enemigoDto);
        Enemigo enemigoCreado = enemigoService.crearEnemigo(enemigo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enemigoCreado.getId())
                .toUri();
        return ResponseEntity.created(location).body(enemigoMapper.toDTO(enemigoCreado));
    }

    /**
     * Actualiza un enemigo existente
     * PUT /api/enemigos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<MostrarEnemigoDto> actualizarEnemigo(
            @PathVariable Long id,
            @Valid @RequestBody CrearEnemigoDto enemigoDto) {
        Enemigo enemigo = enemigoMapper.toEntity(enemigoDto);
        Enemigo enemigoActualizado = enemigoService.actualizarEnemigo(id, enemigo);
        return ResponseEntity.ok(enemigoMapper.toDTO(enemigoActualizado));
    }

    /**
     * Elimina un enemigo
     * DELETE /api/enemigos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnemigo(@PathVariable Long id) {
        enemigoService.eliminarEnemigo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Aplica daño a un enemigo
     * POST /api/enemigos/{id}/danio?cantidad=20
     */
    @PostMapping("/{id}/danio")
    public ResponseEntity<MostrarEnemigoDto> aplicarDanio(
            @PathVariable Long id,
            @RequestParam int cantidad) {
        Enemigo enemigo = enemigoService.aplicarDanio(id, cantidad);
        return ResponseEntity.ok(enemigoMapper.toDTO(enemigo));
    }

    /**
     * Cura a un enemigo
     * POST /api/enemigos/{id}/curar?cantidad=15
     */
    @PostMapping("/{id}/curar")
    public ResponseEntity<MostrarEnemigoDto> curarEnemigo(
            @PathVariable Long id,
            @RequestParam int cantidad) {
        Enemigo enemigo = enemigoService.curarEnemigo(id, cantidad);
        return ResponseEntity.ok(enemigoMapper.toDTO(enemigo));
    }

    /**
     * Restaura la vida completa de un enemigo
     * POST /api/enemigos/{id}/restaurar
     */
    @PostMapping("/{id}/restaurar")
    public ResponseEntity<MostrarEnemigoDto> restaurarVida(@PathVariable Long id) {
        Enemigo enemigo = enemigoService.restaurarVida(id);
        return ResponseEntity.ok(enemigoMapper.toDTO(enemigo));
    }
}