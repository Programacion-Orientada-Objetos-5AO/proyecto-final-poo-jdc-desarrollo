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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.jdecadido.rpg.entity.Item;
import ar.edu.huergo.jdecadido.rpg.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> listarItems() {
        return ResponseEntity.ok(itemService.obtenerTodosLosItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> obtenerItem(@PathVariable Long id) {
        Item item = itemService.obtenerItemPorId(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> crearItem(@RequestBody Item item) {
        Item creado = itemService.crearItem(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> actualizarItem(@PathVariable Long id, @RequestBody Item item) {
        Item actualizado = itemService.actualizarItem(id, item);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}
