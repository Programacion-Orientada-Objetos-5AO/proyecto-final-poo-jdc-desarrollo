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

import ar.edu.huergo.jdecadido.rpg.dto.ItemDto;
import ar.edu.huergo.jdecadido.rpg.entity.Item;
import ar.edu.huergo.jdecadido.rpg.mapper.ItemMapper;
import ar.edu.huergo.jdecadido.rpg.service.ItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<List<ItemDto>> listarItems() {
        List<Item> items = itemService.obtenerTodosLosItems();
        List<ItemDto> itemsDto = itemMapper.toDTOList(items);
        return ResponseEntity.ok(itemsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> obtenerItem(@PathVariable Long id) {
        Item item = itemService.obtenerItemPorId(id);
        ItemDto itemDto = itemMapper.toDTO(item);
        return ResponseEntity.ok(itemDto);
    }

    @PostMapping
    public ResponseEntity<ItemDto> crearItem(@Valid @RequestBody ItemDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        Item creado = itemService.crearItem(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        return ResponseEntity.created(location).body(itemMapper.toDTO(creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> actualizarItem(@PathVariable Long id, @Valid @RequestBody ItemDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        Item actualizado = itemService.actualizarItem(id, item);
        return ResponseEntity.ok(itemMapper.toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        itemService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}