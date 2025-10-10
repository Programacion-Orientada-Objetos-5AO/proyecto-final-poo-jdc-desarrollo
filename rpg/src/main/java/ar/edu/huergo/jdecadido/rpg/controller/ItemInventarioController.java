package ar.edu.huergo.jdecadido.rpg.controller;

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

import ar.edu.huergo.jdecadido.rpg.dto.AgregarItemInventarioDto;
import ar.edu.huergo.jdecadido.rpg.dto.ItemInventarioDto;
import ar.edu.huergo.jdecadido.rpg.entity.ItemInventario;
import ar.edu.huergo.jdecadido.rpg.service.ItemInventarioService;
import jakarta.validation.Valid;

/**
 * Controller para gestionar el inventario de un personaje.
 * Todos los endpoints están bajo /api/personajes/{personajeId}/inventario
 */
@RestController
@RequestMapping("/api/personajes/{personajeId}/inventario")
public class ItemInventarioController {

    @Autowired
    private ItemInventarioService inventarioService;

    /**
     * Lista todos los items en el inventario de un personaje
     * GET /api/personajes/{personajeId}/inventario
     */
    @GetMapping
    public ResponseEntity<List<ItemInventarioDto>> listarInventario(@PathVariable Long personajeId) {
        List<ItemInventario> inventario = inventarioService.listarInventario(personajeId);
        
        List<ItemInventarioDto> inventarioDto = inventario.stream()
            .map(inv -> new ItemInventarioDto(
                inv.getItem().getId(),
                inv.getItem().getNombre(),
                inv.getCantidad(),
                inv.isEquipado()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(inventarioDto);
    }

    /**
     * Agrega un item al inventario de un personaje
     * POST /api/personajes/{personajeId}/inventario
     */
    @PostMapping
    public ResponseEntity<ItemInventarioDto> agregarItem(
            @PathVariable Long personajeId,
            @Valid @RequestBody AgregarItemInventarioDto dto) {
        
        ItemInventario inv = inventarioService.agregarItem(personajeId, dto.getItemId(), dto.getCantidad());
        
        ItemInventarioDto inventarioDto = new ItemInventarioDto(
            inv.getItem().getId(),
            inv.getItem().getNombre(),
            inv.getCantidad(),
            inv.isEquipado()
        );
        
        return ResponseEntity.ok(inventarioDto);
    }

    /**
     * Equipar/desequipar un item del inventario
     * PUT /api/personajes/{personajeId}/inventario/{itemId}/equipar
     */
    @PutMapping("/{itemId}/equipar")
    public ResponseEntity<ItemInventarioDto> equiparItem(
            @PathVariable Long personajeId,
            @PathVariable Long itemId) {
        
        ItemInventario inv = inventarioService.equiparItem(personajeId, itemId);
        
        ItemInventarioDto inventarioDto = new ItemInventarioDto(
            inv.getItem().getId(),
            inv.getItem().getNombre(),
            inv.getCantidad(),
            inv.isEquipado()
        );
        
        return ResponseEntity.ok(inventarioDto);
    }

    /**
     * Busca un item específico en el inventario
     * GET /api/personajes/{personajeId}/inventario/{itemId}
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemInventarioDto> buscarItem(
            @PathVariable Long personajeId,
            @PathVariable Long itemId) {
        
        ItemInventario inv = inventarioService.buscarItem(personajeId, itemId);
        
        ItemInventarioDto inventarioDto = new ItemInventarioDto(
            inv.getItem().getId(),
            inv.getItem().getNombre(),
            inv.getCantidad(),
            inv.isEquipado()
        );
        
        return ResponseEntity.ok(inventarioDto);
    }

    /**
     * Quita un item del inventario de un personaje
     * DELETE /api/personajes/{personajeId}/inventario/{itemId}
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> quitarItem(
            @PathVariable Long personajeId,
            @PathVariable Long itemId) {
        
        inventarioService.quitarItem(personajeId, itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Borra todo el inventario de un personaje
     * DELETE /api/personajes/{personajeId}/inventario
     */
    @DeleteMapping
    public ResponseEntity<Void> borrarInventario(@PathVariable Long personajeId) {
        inventarioService.borrarInventario(personajeId);
        return ResponseEntity.noContent().build();
    }
}