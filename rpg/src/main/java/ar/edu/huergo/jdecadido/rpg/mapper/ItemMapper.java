
package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.ItemDto;
import ar.edu.huergo.jdecadido.rpg.entity.Item;

@Component
public class ItemMapper {

    /**
     * Convierte una entidad Item a DTO
     */
    public ItemDto toDTO(Item item) {
        if (item == null) return null;
        
        return new ItemDto(
            item.getId(),
            item.getNombre(),
            item.getTipo(),
            item.getDescripcion(),
            item.getPoder()
        );
    }

    /**
     * Convierte un DTO a entidad Item
     */
    public Item toEntity(ItemDto dto) {
        if (dto == null) return null;
        
        Item item = new Item();
        item.setId(dto.getId());
        item.setNombre(dto.getNombre());
        item.setTipo(dto.getTipo());
        item.setDescripcion(dto.getDescripcion());
        item.setPoder(dto.getPoder());
        
        return item;
    }

    /**
     * Convierte una lista de entidades a lista de DTOs
     */
    public List<ItemDto> toDTOList(List<Item> items) {
        if (items == null) return List.of();
        
        return items.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}