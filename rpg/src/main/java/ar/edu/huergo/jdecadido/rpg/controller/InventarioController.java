package ar.edu.huergo.jdecadido.rpg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.jdecadido.rpg.entity.Inventario;
import ar.edu.huergo.jdecadido.rpg.service.InventarioService;

@RestController
@RequestMapping("/api/personajes/{personajeId}/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventario(@PathVariable Long personajeId) {
        List<Inventario> inventario = inventarioService.listarInventario(personajeId);
        return ResponseEntity.ok(inventario);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<Inventario> agregarItem(@PathVariable Long personajeId,
                                                  @PathVariable Long itemId,
                                                  @RequestParam(defaultValue = "1") int cantidad) {
        Inventario inv = inventarioService.agregarItem(personajeId, itemId, cantidad);
        return ResponseEntity.ok(inv);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> quitarItem(@PathVariable Long personajeId,
                                           @PathVariable Long itemId) {
        inventarioService.quitarItem(personajeId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Inventario> buscarItem(@PathVariable Long personajeId,
                                                 @PathVariable Long itemId) {
        Inventario inv = inventarioService.buscarItem(personajeId, itemId);
        return ResponseEntity.ok(inv);
    }

    @DeleteMapping
    public ResponseEntity<Void> borrarInventario(@PathVariable Long personajeId) {
        inventarioService.borrarInventario(personajeId);
        return ResponseEntity.noContent().build();
    }
}
